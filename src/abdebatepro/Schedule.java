package abdebatepro;
//========================== Imports =========================================//
import java.sql.Connection;
import java.sql.Date;
//import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.time.DateUtils;

public class Schedule {
    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore;
    public String FirstTeam, SecondTeam;
    public Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    //========================== Constructors ================================//        
    public Schedule(){
        MatchNumber = 0;
        FirstTeamScore = 0;
        SecondTeamScore = 0;
        FirstTeam = "";
        SecondTeam = "";
        MatchDate = null;
    }
    
    public Schedule(int m, int s1, int s2, String ft, String st, Date d){
        MatchNumber = m;
        FirstTeamScore = s1;
        SecondTeamScore = s2;
        FirstTeam = ft;
        SecondTeam = st;
        MatchDate = d;
    }
    //========================== Behaviors ===================================//
    public void setMatchNumber (int m) {MatchNumber = m; }
    public int getMatchNumber() { return MatchNumber; }
    
    public void setFirstTeamScore (int s1) {FirstTeamScore = s1; }
    public int getFirstTeamScore() { return FirstTeamScore; }
    
    public void setSecondTeamScore (int s2) {SecondTeamScore = s2; }
    public int getSecondTeamScore() { return SecondTeamScore; }
    
    public void setFirstTeam (String ft) {FirstTeam = ft; }
    public String getFirstTeam() { return FirstTeam; }
    
    public void setSecondTeam (String st) {SecondTeam = st; }
    public String getSeondTeam() { return SecondTeam; }
    
    public void setMatchDate (Date d) {MatchDate = d; }
    public Date getMatchDate() { return MatchDate; }
    

    //========================== DB Commands =================================//
    //========================== SetupDB =====================================//
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
            stmt = c1.createStatement();
	}
	catch (Exception e) {
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
            }
	c1.close();
	} catch (Exception e) {
                System.out.println(e);
	}
    }
    //========================== InsertDB ====================================//
    public void insertDB(int m, String ft, String st, int s1, int s2, Date d) {
        MatchNumber = m;
        FirstTeam = ft;
        SecondTeam = st;
        FirstTeamScore = s1;
        SecondTeamScore = s2;
        MatchDate = d;
        setupDB();
        try {
            
            //String sql = "insert into Schedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate)  values " + "(" + m + ",'" + ft + "','" + st + "'," + s1 + "," + s2+ ",'" + d  + "')";
            String sql = "insert into Schedule (MatchNumber, FirstTeam, SecondTeam, FirstTeamScore, SecondTeamScore, MatchDate)  values  (?, ?, ?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setInt(1, m);
            pstmt.setString(2, ft);
            pstmt.setString(3, st);
            pstmt.setInt(4, s1);
            pstmt.setInt(5, s2);
            pstmt.setDate(6, d);
            int z = pstmt.executeUpdate();
            if (z == 1) {
                System.out.println("Insert successful");
            }
            else System.out.println("Insert Failed");
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
    //========================== UpdateDB ====================================//
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
            }
            else 
            {
                System.out.println("Update Failed");
            }
            pstmt.close();
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
            }
            else System.out.println("Delete Failed");
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
            if (n == 1) {
                    System.out.println("Delete successful");
            }
            else System.out.println("Delete Failed");
            c1.close();	
        } catch (Exception fe) {
                    System.out.println(fe);
        }
    }
	
    public void display() {
        /*System.out.println("FirstTeamScore:       	               = " + getFirstTeamScore());
	System.out.println("Team Name:                     = " + getTeamName());
	System.out.println("Team Score:                    = " + getT());*/
    } //end display
    //========================== Main ========================================//
    public static void main(String[] args) {
      Schedule s1 = new Schedule();
      s1.selectDB(4);
    }
}
