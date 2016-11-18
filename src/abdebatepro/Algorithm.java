package abdebatepro;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.concurrent.Task;
import javax.swing.JOptionPane;

public class Algorithm {

    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore, count;
    public String FirstTeam, SecondTeam;
    public java.sql.Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    String[][] matchupAPN;
    int matchupNumber;
    static boolean repeat = false, repeat2 = false;
    static int noOfElements;
    static int counter1 = 0;
    static int counter2 = 0;
    static int counter3 = 0;
    static int counter4 = 0;
    static int timeCounter = 0;
    static int timeCounter2 = 0;
    static int timeCounter3 = 0;
    static int timeCounter4 = 0;

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
            count = 0;
            String sql = "SELECT count(*) AS total from Teams";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "No teams are added to the database, please add some teams.", "Error!" + "", JOptionPane.INFORMATION_MESSAGE);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void makeSch() {
        setupDB();
        int numberOfTeam = count;
        matchupNumber = (numberOfTeam * (numberOfTeam - 1)) / 2;
        matchupAPN = new String[matchupNumber][4];
        String[] teamName = new String[numberOfTeam];

        Teams t = new Teams();
        t.selectAllDB();
        //input the team names
        for (int i = 0; i < numberOfTeam; i++) {
            teamName[i] = t.allTeams.get(i);
        }

        //auto input
        //for (int i = 0; i < numberOfTeam; i++)
        //teamName[i] = Integer.toString(i);
        //Generates matchups and saves in matchup[] array
        //matchupAPN = matchupGenerator(teamName, teamName.length);
        String[][] abb = new String[matchupNumber][4];
        int abbCounter = 0;
        for (int i = 0; i < numberOfTeam - 1; i++) {
            for (int j = 0; j < numberOfTeam - 1 - i; j++) {
                //System.out.println(j + " " + (i+j+1));
                abb[abbCounter][1] = teamName[j];
                abb[abbCounter][2] = teamName[(i + j + 1)];
                abbCounter++;
            }
        }
        String[][] temp = new String[1][4];
        temp[0][0] = abb[0][0];
        abb[0][0] = abb[matchupNumber - 1][0];
        abb[matchupNumber - 1][0] = temp[0][0];
        temp[0][1] = abb[0][1];
        abb[0][1] = abb[matchupNumber - 1][1];
        abb[matchupNumber - 1][1] = temp[0][1];
        temp[0][2] = abb[0][2];
        abb[0][2] = abb[matchupNumber - 1][2];
        abb[matchupNumber - 1][2] = temp[0][2];
        //for (int i = 0; i < 45; i++)
        //System.out.println(abb[i][1]+ " vs " + abb[i][2]);

        int noOfElements = ((count - 1) * count) / 2;
        int weeklyGames1 = noOfElements / 10 + 1;
        int weeklyGames2 = noOfElements / 10;
        int[] weekPart = new int[11];
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
        weekPart[10] = noOfElements;
        for (int i = 0; i < 11; i++) {
            System.out.println(weekPart[i]);
        }
        int numberOfWeeks = 10;
        String[] timeSlot = new String[noOfElements];
        int[] weekGameNumber = new int[11];
        for (int i = 0; i < 11; i++) {
            weekGameNumber[i] = weekPart[i];
        }

        for (int i = 0; i < matchupNumber; i++) {
            matchupAPN[i][0] = abb[i][1] + " vs " + abb[i][2];
            matchupAPN[i][1] = abb[i][1];
            matchupAPN[i][2] = abb[i][2];
        }
    }

    public void insertSch() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.deleteAllDB();
        int matchupConstant = 0;
        //java.sql.Date date1 = java.sql.Date.valueOf("2016-10-27");
        Algorithm.randomization(matchupAPN, count);

        Date[] weekOfGames = new Date[(count * (count - 1)) / 2];
        weekOfGames = getDate(count);

        int noOfElements = ((count - 1) * count) / 2;
        int weeklyGames1 = noOfElements / 10 + 1;
        int weeklyGames2 = noOfElements / 10;
        int[] weekPart = new int[11];
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
        weekPart[10] = noOfElements;
        for (int i = 0; i < 11; i++) {
            System.out.println(weekPart[i]);
        }

        String[] timeSlot = new String[noOfElements];
        int a = 0;

        //all weeks
        //thread this
        for (int x = 0; x < 10; x++) {
            for (int i = weekPart[x]; i < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i++) {
                s1.selectDB(i);
                timeSlot[i] = "9 A.M.";
                for (int j = 0; j < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x] - i; j++) {
                    s2.selectDB(i + j);
                    if (s1.FirstTeam.equals(s2.FirstTeam)) {
                        a++;
                    }
                    if (s1.FirstTeam.equals(s2.SecondTeam)) {
                        a++;
                    }
                }
                if (a >= 2) {
                    timeSlot[i] = "3 P.M.";
                }
                a = 0;
            }
            for (int i = (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i < weekPart[x + 1]; i++) {
                s1.selectDB(i);
                timeSlot[i] = "3 P.M.";
                for (int j = 0; j < weekPart[x + 1] - i; j++) {
                    s2.selectDB(i + j);
                    if (s1.FirstTeam.equals(s2.FirstTeam)) {
                        a++;
                    }
                    if (s1.FirstTeam.equals(s2.SecondTeam)) {
                        a++;
                    }
                }
                if (a >= 2) {
                    timeSlot[i] = "9 A.M.";
                }
                a = 0;
            }
        }

        for (int i = 0; i < matchupNumber; i++) {
            s1.insertDB(i + 1, matchupAPN[i][1], matchupAPN[i][2], 0, 0, new java.sql.Date(weekOfGames[i].getTime()), timeSlot[i]);
        }

    }

    public Date[] getDate(int numberOfTeams) {
        String dt = "";  // Start date
        int numberOfGames = (count * (count - 1)) / 2;
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
                //Date startDate1 = df.parse(dt);
                //ABDebatePro.startDate = new java.sql.Date(startDate1.getTime());
                System.out.println("Week #" + (i / 7 + 1) + ": " + d.sDate);
                System.out.println(df + " " + dt);
                dateOfWeeks[i / 7] = df.parse(dt);
            } catch (Exception e) {
                System.out.println(e);
            }
            //ABDebatePro.startDate = startDate1;
            //System.out.println(dTemp);
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
        //for (int i = 0; i < numberOfGames; i++){
        //    System.out.println(weekOfGames[i]);
        //}
        return weekOfGames;
    }

    public static String[][] randomization(String[][] array, int noOfTeams) {
        Schedule s1 = new Schedule();
        noOfElements = array.length;

        int weeklyGames1 = noOfElements / 10 + 1;
        int weeklyGames2 = noOfElements / 10;
        int[] weekPart = new int[11];
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
        weekPart[10] = noOfElements;

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

        int[] weeklyConstant = new int[10];
        for (int i = 0; i < noOfElements % 10; i++) {
            weeklyConstant[i] = (i + 1) * (noOfElements / 10 + 1);
        }
        for (int i = 0; i < 10 - noOfElements % 10; i++) {
            weeklyConstant[i + noOfElements % 10] = (i + 1) * (noOfElements / 10) + weeklyConstant[a % 10 - 1] - 1;
        }
        //for (int i = 0; i < 10; i++)
        //System.out.println(weeklyConstant[i]);

        do {
            repeat = false;
            repeat2 = false;
            for (int i = 0; i < noOfElements; i++) {
                int s = i + (int) (Math.random() * (noOfElements - i));
                String[][] temp = new String[1][4];
                temp[0][0] = array[s][0];
                array[s][0] = array[i][0];
                array[i][0] = temp[0][0];

                temp[0][1] = array[s][1];
                array[s][1] = array[i][1];
                array[i][1] = temp[0][1];

                temp[0][2] = array[s][2];
                array[s][2] = array[i][2];
                array[i][2] = temp[0][2];

                temp[0][3] = array[s][3];
                array[s][3] = array[i][3];
                array[i][3] = temp[0][3];
            }

            counter1 = 0;
            counter2 = 0;
            counter3 = 0;
            counter4 = 0;

            String n1 = "";
            String n2 = "";

            //time slot
            //all weeks
            for (int x = 0; x < 10; x++) {
                timeSlotThreading(weekPart, x, array);
            }
            System.out.println("it's done");
        } while (repeat || repeat2);

        return array;
    }
static int x1 = 0;
    public static void timeSlotThreading(int[] weekPart, int x, String[][] array) {
        System.out.println("This is working on: " + x1);
                    x1++;
        Task task = new Task<Void>() {
            @Override
            public Void call(){
                for (int i = weekPart[x]; i < weekPart[(x + 1)]; i++) {
                    for (int j = 1; j < 3; j++) {
                        for (int k = weekPart[x]; k < weekPart[(x + 1)]; k++) {
                            if (array[i][1].equals(array[k][j])) {
                                timeCounter++;
                            }
                            if (array[i][2].equals(array[k][j])) {
                                timeCounter2++;
                            }
                        }
                    }
                    if ((timeCounter >= 2 && timeCounter2 >= 2) || timeCounter > 2 || timeCounter2 > 2) {
                        repeat2 = true;
                    }

                    timeCounter = 0;
                    timeCounter2 = 0;
                    
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
    }

}
