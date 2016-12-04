package abdebatepro;
//========================== Imports =========================================//
import java.awt.*;
import javax.swing.*;
import java.sql.Date;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
public class ABDebatePro extends JFrame {
    //========================== Declarations ==================================
    MainPanel mp = new MainPanel();
    //public static final String DBURL = "jdbc:ucanaccess://src\\abdebatepro\\DebateDB.accdb";
    public static final String DBURL = "jdbc:ucanaccess://DebateDB.accdb";
    public static ABDebatePro ab;
    public static Date startDate;
    //public static final String DBURL = "jdbc:sqlserver://abdebatejava.cf0ik3ogduzd.us-west-2.rds.amazonaws.com:1433;databseName=abdebatejava;user=root;"
    //+ "password=abkennesaw123";
    //========================== Empty Constructor =============================
    public ABDebatePro() {
        setupGUI();
        initialSetup();
    }
    //========================== GUI Setup======================================
    public void setupGUI() {
        this.add(mp, BorderLayout.CENTER);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1280, 720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Main Page");
        this.setVisible(true);
    }
    //========================== Refresh To Populate ===========================
    public void initialSetup() {
        mp.populateDropBox();
        mp.Refresh();
    }
    //========================== Main Method ===================================
    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            System.out.println(e);
        }
        ab = new ABDebatePro();
    }
}
