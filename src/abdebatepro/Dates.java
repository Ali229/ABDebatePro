package abdebatepro;
//========================== Imports =========================================//
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Dates {
    //========================== Properties ==================================//
    public Date sDate;
    private Connection c1;
    private PreparedStatement pstmt;
    private ResultSet rs;
    //========================== Constructors ================================//        
    public Dates(){
    }
    //========================== Behaviors ===================================//
    public void setsDate (Date d) {sDate = d; }
    public Date getsDate() { return sDate; }
    //========================== DB Commands =================================//
    //========================== SetupDB =====================================//
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
	}
	catch (Exception e) {
            System.out.println(e);
	}
    }
    //========================== SelectDB ====================================//
    public void selectDB() {
        setupDB();
        try {
            String sql = "SELECT StartDate from Dates";
            pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sDate = rs.getDate("StartDate");
            }
            c1.close();
            pstmt.close();
            }   catch (Exception e) {
             System.out.println(e);
            
        }
    }
    //========================== InsertDB ====================================//
    public void insertDB(Date d) {
        setupDB();
        sDate = d;
        try {
            String sql = "insert into Dates (startDate) values (?)";
            System.out.println(sql + d);
            pstmt = c1.prepareStatement(sql);
            pstmt.setDate(1, d);
            
            int z = pstmt.executeUpdate();
            if (z == 1) {
                System.out.println("Insert successful");
            }
            else {
                System.out.println("Insert Failed");
            }
            c1.close();
            pstmt.close();
            }   catch (Exception e) {
             System.out.println(e);
            
        }
    }
    //========================== UpdateDB ====================================//
    public void updateDB(Date d) {
        setupDB();
	try {
            //String sql = "UPDATE Dates SET StartDate = ? LIMIT 1";
            //String sql = "UPDATE Dates SET MAX(StartDate) = ?";
            String sql = "UPDATE Dates SET StartDate = ? where StartDate = (SELECT TOP 1 StartDate  FROM Dates)";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setDate(1, d);
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
    /*public void deleteDB() {
        setupDB();
        try {
            String sql = "delete from Teams where ID = " + ID;
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
    }*/
    //========================== Main Method =================================//
    public static void main(String[] args) {
    }
}
