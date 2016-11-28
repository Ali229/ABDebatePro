/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author marielmcneil
 */
public class Logins {
    
    
    //========================== Properties ==================================//
    public String username, password, email, privilege;
    private Connection c1;
    private PreparedStatement pstmt;
    private ResultSet rs;
    //========================== Constructors ================================//        
    public Logins(){
        
    }
    //========================== Behaviors ===================================//
    public void setUser (String u) {username = u; }
    public String getUser() { return username; }
    
    public void setPass (String p) {password = p; }
    public String getPass() { return password; }
    
    public void setEmail (String e) {email = e; }
    public String getEmail() { return email; }
    
    public void setPrivilege (String priv) {privilege = priv; }
    public String getPrivilege() { return privilege; }
    
    
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
            String sql = "SELECT * from Logins";
            pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                username = rs.getString("Username");
                password = rs.getString("Password");
                email = rs.getString("Email");
                System.out.println("user  " + username);
                System.out.println("pass " + password);
                System.out.println("Email : " + email);
            }
            c1.close();
            pstmt.close();
            }   catch (Exception e) {
             System.out.println(e);
            
        }
    }
    //========================== InsertDB ====================================//
    public void insertDB(String username, String password, String privilege, String email) {
        setupDB();
        try {
            String sql = "insert into Logins (Username, Password, Privilege, Email) values (?,?,?,?)";
            System.out.println(sql + sql);
            pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, privilege);
            pstmt.setString(4, email);
            
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
    public void updateDB(String username, String password, String email) {
        setupDB();
	try {
            //String sql = "UPDATE Dates SET StartDate = ? LIMIT 1";
            //String sql = "UPDATE Dates SET MAX(StartDate) = ?";
            String sql = "UPDATE Logins SET Password = ?, Email = ? where Username = ?";
            System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            pstmt.setString(3, username);
            
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
    public void deleteDB(String username) {
        setupDB();
        try {
            String sql = "delete from Logins where Username = ?" ;
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, username);
            System.out.println(sql);
            int n = pstmt.executeUpdate();
            if (n == 1) {
                System.out.println("Delete successful");
            }
            else System.out.println("Delete Failed");
            c1.close();	
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
    //========================== Main Method =================================//
    
    public static void main(String[] args) {
        Logins l1 = new Logins();
        //l1.insertDB("Mariel", "tong", "Super Referee", "example@example.com");
        //l1.selectDB();
        l1.updateDB("Mariel", "Ali", "Example1@exmaple.com");
        l1.deleteDB("Mariel");
        
        
    }
    
    
    
    
    
    
    
}
