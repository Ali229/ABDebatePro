/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;
import static abdebatepro.Schedule.datesList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author AliNa
 */
public class TieSchedule {
    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore;
    public String FirstTeam, SecondTeam, Time;
    public Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    private PreparedStatement pstmt;
    //========================== Constructors ================================//        
    public TieSchedule() {
        MatchNumber = 0;
        FirstTeamScore = 0;
        SecondTeamScore = 0;
        FirstTeam = "";
        SecondTeam = "";
        MatchDate = null;
        Time = "";
    }
    public TieSchedule(int m, int s1, int s2, String ft, String st, Date d, String t) {
        MatchNumber = m;
        FirstTeamScore = s1;
        SecondTeamScore = s2;
        FirstTeam = ft;
        SecondTeam = st;
        MatchDate = d;
        Time = t;
    }
    //========================== Behaviors ===================================//
    public void setMatchNumber(int m) {
        MatchNumber = m;
    }
    public int getMatchNumber() {
        return MatchNumber;
    }
    public void setFirstTeamScore(int s1) {
        FirstTeamScore = s1;
    }
    public int getFirstTeamScore() {
        return FirstTeamScore;
    }
    public void setSecondTeamScore(int s2) {
        SecondTeamScore = s2;
    }
    public int getSecondTeamScore() {
        return SecondTeamScore;
    }
    public void setFirstTeam(String ft) {
        FirstTeam = ft;
    }
    public String getFirstTeam() {
        return FirstTeam;
    }
    public void setSecondTeam(String st) {
        SecondTeam = st;
    }
    public String getSeondTeam() {
        return SecondTeam;
    }
    public void setMatchDate(Date d) {
        MatchDate = d;
    }
    public Date getMatchDate() {
        return MatchDate;
    }
    public void setTime(String t) {
        Time = t;
    }
    public String getTime() {
        return Time;
    }
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
    //========================== SelectDB ====================================//
    public void selectDB(int m) {
        setupDB();
        try {
            System.out.println("select * from TieSchedule where MatchNumber = " + m);
            ResultSet rs = stmt.executeQuery("select * from TieSchedule where MatchNumber = " + m);
            while (rs.next()) {
                MatchNumber = rs.getInt("MatchNumber");
                FirstTeam = rs.getString("FirstTeam");
                SecondTeam = rs.getString("SecondTeam");
                FirstTeamScore = rs.getInt("FirstTeamScore");
                SecondTeamScore = rs.getInt("SecondTeamScore");
                MatchDate = rs.getDate("MatchDate");
                Time = rs.getString("Time");
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== UpdateDB ====================================//
    public void updateScore(int m, int s1, int s2) {
        setupDB();
        try {
            String sql = "update TieSchedule set FirstTeamScore = ?, SecondTeamScore = ? WHERE MatchNumber = ?";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setInt(1, s1);
            pstmt.setInt(2, s2);
            pstmt.setInt(3, m);
            int z = pstmt.executeUpdate();;
            if (z == 1) {
                System.out.println("Update successful");
            } else {
                System.out.println("Update Failed");
            }
            pstmt.close();
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    public void tie() {
        setupDB();
        String teamName = "";
        ArrayList<String> tieTeamNames = new ArrayList<String>();
        int teamScore = 0;
        try {
            setupDB();
            System.out.println("select * from Teams ORDER BY ORDER BY TeamScore");
            ResultSet rs = stmt.executeQuery("select * from Teams ORDER BY TeamScore");
            while (rs.next()) {
                teamName = rs.getString("TeamName");
                teamScore = rs.getInt("TeamScore");
                System.out.println("score " + teamName + " " + teamScore);
            }
            System.out.println(teamScore);
            if (teamScore > 0) {
                ResultSet rs2 = stmt.executeQuery("select * from Teams where TeamScore = " + teamScore);
                while (rs2.next()) {
                    tieTeamNames.add(rs2.getString("TeamName"));
                }
                String[] tieTeam = new String[tieTeamNames.size()];
                String[][] tieMatchup = new String[100][3];
                int tieMatchupCounter = 0;
                for (int i = 0; i < tieTeamNames.size(); i++) {
                    tieTeam[i] = tieTeamNames.get(i);
                    System.out.println(tieTeam[i]);
                }
                for (int i = 0; i < tieTeam.length - 1; i++) {
                    for (int j = 0; j < tieTeam.length - i - 1; j++) {
                        //System.out.println(j + " " + (i+j+1));
                        tieMatchup[tieMatchupCounter][0] = tieTeam[j] + " vs " + tieTeam[i + j + 1];
                        tieMatchup[tieMatchupCounter][1] = tieTeam[j];
                        tieMatchup[tieMatchupCounter][2] = tieTeam[(i + j + 1)];
                        tieMatchupCounter++;
                    }
                }
                int count = tieTeam.length;
                int noOfElements = ((count - 1) * count) / 2;
                int matchupNumber = noOfElements;
                int weeklyGames1 = noOfElements / 10 + 1;
                int weeklyGames2 = noOfElements / 10;
                int[] weekPart = new int[11];
                String[][] matchupAPN = new String[noOfElements][3];
                for (int i = 0; i < noOfElements; i++) {
                    matchupAPN[i][0] = tieMatchup[i][0];
                    matchupAPN[i][1] = tieMatchup[i][1];
                    matchupAPN[i][2] = tieMatchup[i][2];
                }
                for (int i = 0; i < noOfElements; i++) {
                    System.out.println(matchupAPN[i][0]);
                    System.out.println(matchupAPN[i][1]);
                    System.out.println(matchupAPN[i][2]);
                    System.out.println("");
                }
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
                weekPart[10] = noOfElements;
                for (int i = 0; i < 11; i++) {
                    System.out.println(weekPart[i]);
                }
                //Assign time slot for all the matchups 
                String[] timeSlot = new String[noOfElements];
                int a = 0;
                int b = 0;
                /*for (int x = 0; x < 10; x++) {
                    for (int i = weekPart[x]; i < (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i++) {
                        timeSlot[i] = "9 A.M.";
                    }
                    for (int i = (weekPart[x + 1] - weekPart[x]) / 2 + weekPart[x]; i < weekPart[x + 1]; i++) {
                        //timeSlot[i] = "3 P.M.";
                    }
                }*/
                
                for (int i = 0; i < noOfElements; i++){
                    timeSlot[i] = i + 9 + " A.M.";
                    if (i > 3){
                        timeSlot[i] = i - 3 + " P.M.";
                    }
                }
                //Date[] weekOfGames = new Date[(count*(count-1))/2];
                //weekOfGames = (Date[]) a1.getDate(count);
                Date fd = null;
                Date d1 = null;
                setupDB();
                try {
                String sql = "select * from Schedule order by MatchNumber";
                pstmt = c1.prepareStatement(sql);
                ResultSet rs3 = stmt.executeQuery(sql);
                while (rs3.next()) {
                    d1 = rs3.getDate("MatchDate");
                    System.out.println("rs3 running times " + d1);
                }
                c1.close();
                } catch (Exception e) {
                System.out.println(e);
                }
                String dt = "";
                Dates d = new Dates();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                Calendar c = Calendar.getInstance();
                
                c.setTime(d1);
                //for (int i = 0; i < 70; i += 7){
                    c.add(Calendar.DATE, 7);
                    System.out.println("after adding "+c.getTime());
                //}
                dt = sdf.format(c.getTime());
                DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                utilDate = sdf.parse(dt);

                deleteTieSchedule();
                System.out.println(matchupNumber);
                for (int i = 0; i < matchupNumber; i++) {
                    System.out.println("fdsadsad");
                    insertTieSchedule(i + 1, matchupAPN[i][1], matchupAPN[i][2], 0, 0, new java.sql.Date(utilDate.getTime()), timeSlot[i]);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== InsertDB ====================================//
    public void insertTieSchedule(int m, String ft, String st, int s1, int s2, Date d, String t) {
        MatchNumber = m;
        FirstTeam = ft;
        SecondTeam = st;
        FirstTeamScore = s1;
        SecondTeamScore = s2;
        MatchDate = d;
        Time = t;
        setupDB();
        System.out.println("this is running");
        try {
            //String sql = "insert into Schedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate)  values " + "(" + m + ",'" + ft + "','" + st + "'," + s1 + "," + s2+ ",'" + d  + "')";
            String sql = "insert into TieSchedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate, Time)  values  (?, ?, ?, ?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setInt(1, m);
            pstmt.setString(2, ft);
            pstmt.setString(3, st);
            pstmt.setInt(4, s1);
            pstmt.setInt(5, s2);
            pstmt.setDate(6, d);
            pstmt.setString(7, t);
            int z = pstmt.executeUpdate();
            if (z == 1) {
                System.out.println("Insert successful");
            } else {
                System.out.println("Insert Failed");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    //========================== DeleteTieSchedule ===========================//
    public void deleteTieSchedule() {
        setupDB();
        try {
            String sql = "delete from TieSchedule";
            System.out.println(sql);
            int n = stmt.executeUpdate(sql);
            if (n > 0) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete Failed on All");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
}
