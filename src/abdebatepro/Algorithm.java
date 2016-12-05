package abdebatepro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Algorithm {
    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore;
    public String FirstTeam, SecondTeam;
    public java.sql.Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    public int numberOfTeams;
    String[][] matchupAPN;
    int matchupNumber;
    //========================== DB Commands =================================//
    //========================== SetupDB =====================================//
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
            stmt = c1.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== Counting Teams ==============================//
    public void count() {
        setupDB();
        try {
            String sql = "SELECT count(*) AS total from Teams";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numberOfTeams = rs.getInt("total");
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void makeSch() {
        matchupNumber = (numberOfTeams * (numberOfTeams - 1)) / 2;
        matchupAPN = new String[matchupNumber][4];
        String[] teamName = new String[numberOfTeams];
        Teams t = new Teams();
        t.selectAllDB();
        //input the team names
        for (int i = 0; i < numberOfTeams; i++) {
            teamName[i] = t.allTeams.get(i);
        }
        //Generates matchups and saves in matchup[] matchupAPN
        //matchupAPN = matchupGenerator(teamName, teamName.length);
        int abbCounter = 0;
        for (int i = 0; i < numberOfTeams - 1; i++) {
            for (int j = 0; j < numberOfTeams - 1 - i; j++) {
                matchupAPN[abbCounter][0] = teamName[j] + " vs " + teamName[i + j + 1];
                matchupAPN[abbCounter][1] = teamName[j];
                matchupAPN[abbCounter][2] = teamName[(i + j + 1)];
                abbCounter++;
            }
        }
    }
    public void insertSch() {
        Schedule s1 = new Schedule();
        s1.deleteAllDB();
        randomization();
        Date[] weekOfGames = new Date[matchupNumber];
        weekOfGames = getDate(numberOfTeams);
        int noOfElements = ((numberOfTeams - 1) * numberOfTeams) / 2;
        int weeklyGames1 = noOfElements / 10 + 1;
        int weeklyGames2 = noOfElements / 10;
        int[] weekPart = new int[11];
        for (int i = 0; i < noOfElements % 10; i++) {
            weekPart[i] = weeklyGames1 * i;
        }
        for (int i = noOfElements % 10; i < 10; i++) {
            int b = noOfElements;
            if (noOfElements % 10 == 0) {
                b = 1;
            }
            weekPart[i] = weeklyGames2 * (i - noOfElements % 10 + 1) + weekPart[b % 10 - 1] + 1;
        }
        if (noOfElements == 10){
            for (int i = 0; i < 10; i++){
                weekPart[i] = i;
            }
        }
        weekPart[10] = noOfElements;
        for (int i = 0; i < 11; i++) {
            System.out.println(weekPart[i]);
        }
        //Assign time slot for all the matchups 
        String[] timeSlot = new String[noOfElements];
        int a = 0;
        int b = 0;
        int randomTime = 0;

        /*String[] times = new String[2];
        times[0] = "9 AM";
        times[1] = "3 PM";*/
        boolean repeatTime = false;
        int repeatMatchupNumber1 = 0;
        int repeatMatchupNumber2 = 0;
        for (int x = 0; x < 10; x++) {
            /*for (int i = weekPart[x]; i < weekPart[(x + 1)]; i++) {
                randomTime = (int) (Math.random() * 2);
                timeSlot[i] = time[randomTime]; 
                    for (int j = 1; j < 3; j++) {
                        for (int k = weekPart[x]; k < weekPart[(x + 1)]; k++) {
                            if (matchupAPN[i][1].equals(matchupAPN[k][j]) && timeSlot[i] == timeSlot[k]) {
                                repeatCounter1++;
                                if (repeatCounter1 > 1){
                                    repeatTime = true;
                                    System.out.println(repeatCounter1);
                                }
                            }
                            if (matchupAPN[i][2].equals(matchupAPN[k][j]) && timeSlot[i] == timeSlot[k]) {
                                repeatCounter2++;
                                if (repeatCounter2 > 1){
                                    repeatTime = true;
                                    System.out.println(repeatCounter2);
                                }
                            }
                        }
                        
                        
                        repeatCounter1 = 0;
                        repeatCounter2 = 0;
                    }
                    
            }*/
            for (int i = weekPart[x]; i < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i++) {
                timeSlot[i] = "9 AM";
                for (int j = 1; j < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x] - i; j++) {
                    if (matchupAPN[i][1].equals(matchupAPN[i + j][1]) || matchupAPN[i][1].equals(matchupAPN[i + j][2])) {
                        a++;
                    }
                    if (matchupAPN[i][2].equals(matchupAPN[i + j][1]) || matchupAPN[i][2].equals(matchupAPN[i + j][2])) {
                        b++;
                    }
                }
                if (a >= 1 || b >= 1) {
                    timeSlot[i] = "3 PM";
                    if (a >= 1) {
                        for (int j = (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; j < weekPart[x + 1]; j++) {
                            if (matchupAPN[i][2].equals(matchupAPN[j][1]) || matchupAPN[i][2].equals(matchupAPN[j][2])) {
                                timeSlot[j] = "9 AM";
                                repeatMatchupNumber1 = j;
                            }
                        }
                    }
                    if (b >= 1) {
                        for (int j = (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; j < weekPart[x + 1]; j++) {
                            if (matchupAPN[i][1].equals(matchupAPN[j][1]) || matchupAPN[i][1].equals(matchupAPN[j][2])) {
                                timeSlot[j] = "9 AM";
                                repeatMatchupNumber2 = j;
                            }
                        }
                    }
                }
                a = 0;
                b = 0;
            }
            for (int i = (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i < weekPart[x + 1]; i++) {
                timeSlot[i] = "3 PM";
                if (i == repeatMatchupNumber1 || i == repeatMatchupNumber2) {
                    timeSlot[i] = "9 AM";
                }
                for (int j = 1; j < weekPart[x + 1] - i; j++) {
                    if (matchupAPN[i][1].equals(matchupAPN[i + j][1]) || matchupAPN[i][1].equals(matchupAPN[i + j][2])) {
                        a++;
                    }
                    if (matchupAPN[i][2].equals(matchupAPN[i + j][1]) || matchupAPN[i][2].equals(matchupAPN[i + j][2])) {
                        b++;
                    }
                }
                if (a >= 1 || b >= 1) {
                    timeSlot[i] = "9 AM";
                    if (a >= 1) {
                        for (int j = weekPart[x]; j < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; j++) {
                            if (matchupAPN[i][2].equals(matchupAPN[j][1]) || matchupAPN[i][2].equals(matchupAPN[j][2])) {
                                timeSlot[j] = "3 PM";
                            }
                        }
                    }
                    if (b >= 1) {
                        for (int j = weekPart[x]; j < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; j++) {
                            if (matchupAPN[i][1].equals(matchupAPN[j][1]) || matchupAPN[i][1].equals(matchupAPN[j][2])) {
                                timeSlot[j] = "3 PM";
                            }
                        }
                    }
                }
                a = 0;
                b = 0;
            }
        }
        for (int i = 0; i < matchupNumber; i++) {
            s1.insertDB(i + 1, matchupAPN[i][1], matchupAPN[i][2], 0, 0, new java.sql.Date(weekOfGames[i].getTime()), timeSlot[i]);
        }
    }
    
    public Date[] getDate(int numberOfTeams) {
        String dt = "";  // Start date
        int numberOfGames = (numberOfTeams * (numberOfTeams - 1)) / 2;
        Date[] dateOfWeeks = new Date[10];
        Date[] weekOfGames = new Date[numberOfGames];
        Dates d = new Dates();
        for (int i = 0; i < 70; i += 7) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Calendar c = Calendar.getInstance();
            d.selectDB();
            c.setTime(d.sDate);
            c.add(Calendar.DATE, i);  // increments the number of days by 7
            dt = sdf.format(c.getTime());
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            try {
                System.out.println("Week #" + (i / 7 + 1) + ": " + d.sDate);
                System.out.println(df + " " + dt);
                dateOfWeeks[i / 7] = df.parse(dt);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        int counter = 0;
        for (int i = 0; i < numberOfGames % 10; i++) {
            for (int j = 0; j < numberOfGames / 10 + 1; j++) {
                weekOfGames[counter] = dateOfWeeks[i];
                counter++;
            }
        }
        for (int i = numberOfGames % 10; i < 10; i++) {
            for (int j = 0; j < numberOfGames / 10; j++) {
                weekOfGames[counter] = dateOfWeeks[i];
                counter++;
            }
        }
        return weekOfGames;
    }
    public void randomization() {
        int noOfElements = matchupAPN.length;
        int timeCounter = 0;
        int timeCounter2 = 0;
        int weeklyGames1 = noOfElements / 10 + 1;
        int weeklyGames2 = noOfElements / 10;
        int[] weekPart = new int[11];
        int[] teamCounter = new int[10];
        int repeatCounter = 0;
        String[] teamNames = new String[10];
        Teams t = new Teams();
        t.selectAllDB();
        //input the team names
        for (int i = 0; i < t.allTeams.size(); i++) {
            teamNames[i] = t.allTeams.get(i);
        }
        for (int i = 0; i < noOfElements % 10; i++) {
            weekPart[i] = weeklyGames1 * i;
        }
        for (int i = noOfElements % 10; i < 10; i++) {
            int a = noOfElements;
            if (noOfElements % 10 == 0) {
                a = 1;
            }
            weekPart[i] = weeklyGames2 * (i - noOfElements % 10 + 1) + weekPart[a % 10 - 1] + 1;
        }
        if (noOfElements == 10){
            for (int i = 0; i < 10; i++){
                weekPart[i] = i;
            }
        }
        weekPart[10] = noOfElements;
        boolean repeat = false;
        int[] weeklyGames = new int[10];
        int a = noOfElements;
        if (noOfElements == 10) {
            a = 1;
        }
        for (int i = 0; i < noOfElements % 10 + 1; i++) {
            weeklyGames[i] = i * (noOfElements / 10 + 1);
        }
        for (int i = 0; i < 10 - noOfElements % 10 - 1; i++) {
            weeklyGames[i + noOfElements % 10 + 1] = (i + 1) * (noOfElements / 10) + weeklyGames[a % 10 - 1];
        }
        do {
            repeat = false;
            for (int i = 0; i < noOfElements; i++) {
                int s = i + (int) (Math.random() * (noOfElements - i));
                String[][] temp = new String[1][4];
                temp[0][0] = matchupAPN[s][0];
                matchupAPN[s][0] = matchupAPN[i][0];
                matchupAPN[i][0] = temp[0][0];
                temp[0][1] = matchupAPN[s][1];
                matchupAPN[s][1] = matchupAPN[i][1];
                matchupAPN[i][1] = temp[0][1];
                temp[0][2] = matchupAPN[s][2];
                matchupAPN[s][2] = matchupAPN[i][2];
                matchupAPN[i][2] = temp[0][2];
            }
            //Check no more than 2 games in a week, possible to have 2 time slot in a day
            for (int x = 0; x < 10; x++) {
                for (int i = weekPart[x]; i < weekPart[(x + 1)]; i++) {
                    for (int j = 1; j < 3; j++) {
                        for (int k = weekPart[x]; k < weekPart[(x + 1)]; k++) {
                            if (matchupAPN[i][1].equals(matchupAPN[k][j])) {
                                timeCounter++;
                            }
                            if (matchupAPN[i][2].equals(matchupAPN[k][j])) {
                                timeCounter2++;
                            }
                        }
                        //check2
                        for (int z = 0; z < t.allTeams.size(); z++) {
                            if (teamNames[z].equals(matchupAPN[i][j])) {
                                teamCounter[z]++;
                            }
                        }
                    }
                    if (timeCounter > 2 || timeCounter2 > 2) {
                        repeat = true;
                    }
                    timeCounter = 0;
                    timeCounter2 = 0;
                }
                //check2
                for (int z = 0; z < t.allTeams.size(); z++) {
                    if (teamCounter[z] >= 2) {
                        repeatCounter++;
                    }
                }
                if (repeatCounter > 2) {
                    repeat = true;
                    //System.out.println("repeat counter = " + repeatCounter + " repeat this ");
                }
                //System.out.println("repeat counter = " + repeatCounter + " repeat = " + repeat);
                for (int z = 0; z < t.allTeams.size(); z++) {
                    //System.out.println("counter " + (z+1) + " = " + teamCounter[z]);
                    teamCounter[z] = 0;
                }
                //System.out.println("");
                repeatCounter = 0;
            }
        } while (repeat);
    }
}
