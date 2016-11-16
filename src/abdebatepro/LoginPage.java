/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author AliNa
 */
public class LoginPage extends javax.swing.JDialog {
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    public static String storeUser = "", storePass = "";
    public static boolean accessGrant = false;
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    /**
     * Creates new form LoginPage
     */
    public LoginPage(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //userTextField.setUI(new HintTextFieldUI("Username", true));
        //passTextField.setUI(new HintTextFieldUI("Password", true));
        okButton.requestFocusInWindow();
        this.getRootPane().setDefaultButton(okButton);
    }
    public LoginPage(java.awt.Frame parent, boolean modal, String n) {
        super(parent, modal);
        initComponents();
        //userTextField.setUI(new HintTextFieldUI("Username", true));
        //passTextField.setUI(new HintTextFieldUI("Password", true));
        userTextField.setEnabled(false);
        passTextField.requestFocusInWindow();
        this.getRootPane().setDefaultButton(okButton);
        LoginPage.accessGrant = false;
        checkLoginStatus();
    }
    //========================== DB Setup=======================================
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        userTextField = new javax.swing.JTextField();
        icon = new javax.swing.JLabel();
        passTextField = new javax.swing.JPasswordField();
        forgetLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        okButton.setText("Login");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        userTextField.setText("Username");
        userTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userTextFieldFocusGained(evt);
            }
        });

        icon.setIcon(UIManager.getIcon("OptionPane.questionIcon"));

        passTextField.setText("Password");
        passTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passTextFieldFocusGained(evt);
            }
        });

        forgetLabel.setForeground(new java.awt.Color(51, 0, 204));
        forgetLabel.setText("Forgot Password");
        forgetLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgetLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(forgetLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(okButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton)
                            .addGap(52, 52, 52)))
                    .addComponent(passTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(passTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(okButton)
                        .addComponent(cancelButton))
                    .addComponent(forgetLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
         String user = userTextField.getText();
         String pass = passTextField.getText();
         setupDB();
         try {
            String sql = "SELECT * FROM Logins where Username=? and Password=?";
            //String sql = "SELECT Username=? and Password=? COLLATE Latin1_General_CS_AS FROM Logins";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            System.out.println(sql);
            rs = pstmt.executeQuery();
        if (rs.next()) {
            //MainPanel.welcomeArea.setText("Welcome " + rs.getString("Username") + "!" +  System.getProperty("line.separator") + rs.getString("Privilege"));
            MainPanel.loginLabel.setText("Welcome " + rs.getString("Username") + "!");
            MainPanel.privLabel.setText(rs.getString("Privilege"));
            MainPanel.bottomPanel.setVisible(true);   
            MainPanel.loginButton.setText("Logout");
            storeUser = rs.getString("Username");
            storePass = rs.getString("Password");
            accessGrant = true;
            MainPanel.settingsButton.setVisible(true);
            if (rs.getString("Privilege").equals("Super Referee")) {
                MainPanel.superPanel.setVisible(true); 
            }
            else if (rs.getString("Privilege").equals("Referee")) {
                MainPanel.superPanel.setVisible(false); 
            }
            doClose(RET_OK);
            
        } else {
               JOptionPane.showMessageDialog(null, "Login Incorrect");
               doClose(RET_OK);
               if (!storeUser.isEmpty() == false) {
                MainPanel.loginButton.doClick();
               }
               accessGrant = false;
        }  
          pstmt.close();
          c1.close();
          }      
         catch (Exception e) {
            System.out.println(e);
          }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void userTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userTextFieldFocusGained
        userTextField.setText("");
    }//GEN-LAST:event_userTextFieldFocusGained

    private void passTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passTextFieldFocusGained
        passTextField.setText("");
    }//GEN-LAST:event_passTextFieldFocusGained

    private void forgetLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgetLabelMouseClicked
        ForgotPassword fp = new ForgotPassword(ABDebatePro.ab, true);
        fp.setVisible(true);
    }//GEN-LAST:event_forgetLabelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginPage dialog = new LoginPage(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
        
    }
    public void checkLoginStatus() {
        if (storeUser.isEmpty() == false) {
            userTextField.setText(storeUser);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel forgetLabel;
    private javax.swing.JLabel icon;
    private javax.swing.JButton okButton;
    public javax.swing.JPasswordField passTextField;
    public javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}
