package abdebatepro;
//========================== Imports =========================================//
import static abdebatepro.ABDebatePro.DBURL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Schedule {
    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore;
    public String FirstTeam, SecondTeam, Time;
    public Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    private PreparedStatement pstmt;
    public String referee;
    public ArrayList<String> allReferee = new ArrayList<String>();
    int totalGames;
    //========================== Constructors ================================//        
    public Schedule() {
        MatchNumber = 0;
        FirstTeamScore = 0;
        SecondTeamScore = 0;
        FirstTeam = "";
        SecondTeam = "";
        MatchDate = null;
        Time = "";
    }
    public Schedule(int m, int s1, int s2, String ft, String st, Date d, String t) {
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
    //========================== SelectDB ====================================//
    public void selectDB(int m) {
        setupDB();
        try {
            System.out.println("select * from Schedule where MatchNumber = " + m);
            ResultSet rs = stmt.executeQuery("select * from Schedule where MatchNumber = " + m);
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
    //========================== InsertDB ====================================//
    public void insertDB(int m, String ft, String st, int s1, int s2, Date d, String t) {
        MatchNumber = m;
        FirstTeam = ft;
        SecondTeam = st;
        FirstTeamScore = s1;
        SecondTeamScore = s2;
        MatchDate = d;
        Time = t;
        setupDB();
        try {
            //String sql = "insert into Schedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate)  values " + "(" + m + ",'" + ft + "','" + st + "'," + s1 + "," + s2+ ",'" + d  + "')";
            String sql = "insert into Schedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate, Time)  values  (?, ?, ?, ?, ?, ?, ?)";
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
    //========================== UpdateDB ====================================//
    /*public void updateDB(String n, int s) {
        setupDB();
	try {
            String sql = "update Schedule set FirstTeam = " + "'" + n + "', SecondTeamScore = " + s + " where id = " + FirstTeamScore;
            System.out.println(sql);
            int z = stmt.executeUpdate(sql);
            if (z == 1) {
                    System.out.println("Update successful");
            }
            else 
            {
                System.out.println("Update Failed");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }*/
    //========================== Update Match Date ===========================//
    public void updateMatchDate(Date d, int m) {
        setupDB();
        try {
            String sql = "update Schedule set MatchDate = ? WHERE MatchNumber = ?";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setDate(1, d);
            pstmt.setInt(2, m);
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
    //========================== Update Score ================================//
    public void updateScore(int m, int s1, int s2) {
        setupDB();
        try {
            String sql = "update Schedule set FirstTeamScore = ?, SecondTeamScore = ? WHERE MatchNumber = ?";
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
    //========================== DeleteTeam from Schedule ====================//
    public void deleteTeam(String teamName) {
        setupDB();
        try {
            String sql = "delete from Schedule where firstTeam = '" + teamName + "'";
            System.out.println(sql);
            int n = stmt.executeUpdate(sql);
            if (n == 1) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete Failed");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
        try {
            String sql = "delete from Schedule where secondTeam = '" + teamName + "'";
            System.out.println(sql);
            int n = stmt.executeUpdate(sql);
            if (n == 1) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete Failed");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    
    //========================== DeleteDB ====================================//
    public void deleteDB() {
        setupDB();
        try {
            String sql = "delete from Schedule where id = " + MatchNumber;
            System.out.println(sql);
            int n = stmt.executeUpdate(sql);
            if (n == 1) {
                System.out.println("Delete successful");
            } else {
                System.out.println("Delete Failed");
            }
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    //========================== DeleteDB ====================================//
    public void deleteAllDB() {
        setupDB();
        try {
            String sql = "delete from Schedule";
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
    public static ArrayList<LocalDate> datesList = new ArrayList();
    Date d1;
    public void storeDate(String n1, String n2) {
        setupDB();
        try {
            String sql = "select MatchDate from Schedule where FirstTeam = ? OR SecondTeam = ?";
            pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, n1);
            pstmt.setString(2, n2);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                d1 = rs.getDate("MatchDate");
                datesList.add(d1.toLocalDate());
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void storeDate2(String n1, String n2) {
        setupDB();
        try {
            String sql = "select MatchDate from Schedule where FirstTeam = ? OR SecondTeam = ?";
            pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, n2);
            pstmt.setString(2, n1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                d1 = rs.getDate("MatchDate");
                datesList.add(d1.toLocalDate());
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== Assigned Referee ============================//
    public void selectReferee() {
        setupDB();
        try {
            String sql = "SELECT * from Logins where Privilege = 'Referee'";
            pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                referee = rs.getString("Username");
                allReferee.add(referee);
            }
            c1.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        count();
    }
    public void count() {
        try {
            Connection c1 = DriverManager.getConnection(DBURL);
            Statement stmt = c1.createStatement();
            ResultSet rs = stmt.executeQuery("select Count(MatchNumber) AS TotalNumber from Schedule");
            while (rs.next()) {
                totalGames = rs.getInt("TotalNumber");
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        updateAssignedReferee();
    }
    public void updateAssignedReferee() {
        setupDB();
        try {
            for (int x = 1; x <= totalGames; x++) {
                //allReferee.forEach(s -> System.out.println(s));
                //int y = 0;
                //String selectedRef = allReferee.get(y);
                String sql = "update Schedule set AssignedReferee = ? where MatchNumber = ?";
                System.out.println(sql);
                PreparedStatement pstmt = c1.prepareStatement(sql);
                pstmt.setString(1, allReferee.get(x%allReferee.size()));
                //pstmt.setString(1, selectedRef);
                pstmt.setInt(2, x);
                int z = pstmt.executeUpdate();;
                if (z == 1) {
                    System.out.println("Update successful");
                } else {
                    System.out.println("Update Failed");
                }
            }
            pstmt.close();
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    //========================== Main ========================================//
    public static void main(String[] args) {
        Schedule s1 = new Schedule();
        s1.selectDB(4);
    }
}
