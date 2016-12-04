package abdebatepro;
//========================== Imports =========================================//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Teams {
    //========================== Properties ==================================//
    public int ID, TeamScore;
    public String TeamName;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    public ArrayList<String> allTeams = new ArrayList<String>();
    public int firstScore, secondScore, numberOfTeam;  //Used to calculate score
    //========================== Constructors ================================//        
    public Teams() {
        ID = 0;
        TeamScore = 0;
        TeamName = "";
    }
    public Teams(int i, int s, String n) {
        ID = i;
        TeamScore = s;
        TeamName = n;
    }
    //========================== Behaviors ===================================//
    public void setID(int i) {
        ID = i;
    }
    public int getID() {
        return ID;
    }
    public void TeamName(String n) {
        TeamName = n;
    }
    public String getTeamName() {
        return TeamName;
    }
    public void setTeamScore(int s) {
        TeamScore = s;
    }
    public int getTeamScore() {
        return TeamScore;
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
    public void selectDB(int d) {
        setupDB();
        try {
            String sql = "select * from Teams where id = " + d;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ID = rs.getInt(1);
                TeamName = rs.getString(2);
                TeamScore = rs.getInt(3);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== SelectDB ====================================//
    public void selectAllDB() {
        setupDB();
        try {
            ResultSet rs = stmt.executeQuery("select * from Teams");
            while (rs.next()) {
                ID = rs.getInt(1);
                TeamName = rs.getString(2);
                allTeams.add(TeamName);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== InsertDB ====================================//
    public void insertDB(String n) {
        TeamName = n;
        setupDB();
        try {
            String sql = "insert into Teams (TeamName) values (?)";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, n);
            System.out.println(sql);
            int z = pstmt.executeUpdate();
            if (z == 1) {
                System.out.println("Insert successful");
            } else {
                System.out.println("Insert Failed");
            }
            c1.close();
            pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Team name must be Unique!", "Error Name" + "", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== UpdateDB ====================================//
    public void updateDB(int i, String n) {
        setupDB();
        try {
            String sql = "update Teams set TeamName = '" + n + "' where ID = " + i;
            System.out.println(sql);
            int z = stmt.executeUpdate(sql);
            if (z == 1) {
                System.out.println("Update successful");
            } else {
                System.out.println("Update Failed");
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
            String sql = "delete from Teams where ID = " + ID;
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
            String sql = "delete from Teams";
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
    //========================== Calculating Team Score On Edit ==============//
    //========================== Step 1 ======================================//
    public void CalculateTeamScore() {
        setupDB();
        try {
            String sql = "SELECT count(*) AS total from Teams";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numberOfTeam = rs.getInt("total");
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        selectAllDB();
        for (int i = 0; i < numberOfTeam; i++) {
            selectFirstScore(allTeams.get(i));
            selectSecondScore(allTeams.get(i));
            updateTeamScore(allTeams.get(i), (firstScore + secondScore));
            firstScore = 0;
            secondScore = 0;
        }
    }
    //========================== Step 2 ======================================//
    public void selectFirstScore(String m) {
        setupDB();
        try {
            String sql = "select * from Schedule where FirstTeam = '" + m + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ID = rs.getInt(1);
                firstScore += rs.getInt(4);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== Step 3 ======================================//
    public void selectSecondScore(String m) {
        setupDB();
        try {
            String sql = "select * from Schedule where SecondTeam = '" + m + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ID = rs.getInt(1);
                secondScore += rs.getInt(5);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //========================== Step 4 ======================================//
    public void updateTeamScore(String m, int s2) {
        setupDB();
        try {
            String sql = "update Teams set TeamScore = ? WHERE TeamName = ?";
            //System.out.println(sql);
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setInt(1, s2);
            pstmt.setString(2, m);
            int z = pstmt.executeUpdate();
            if (z == 1) {
                //System.out.println("Update successful");
            } else {
                //System.out.println("Update Failed");
            }
            pstmt.close();
            c1.close();
        } catch (Exception fe) {
            System.out.println(fe);
        }
    }
}
