/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/**
 *
 * @author marielmcneil
 */
public class Settings extends javax.swing.JDialog {
    PreparedStatement pstmt;
    public Connection c1;
    public Statement stmt;
    public String user, Email;
    String currentPassword, newPassword, passwordConfirmed;
    /**
     * Creates new form Settings
     */
    //Constructor for Super Referee
    public Settings(java.awt.Frame parent, boolean modal, String u, String s) {
        super(parent, modal);
        initComponents();
        user = u;
    }
    //Constructor for Referee
    public Settings(java.awt.Frame parent, boolean modal, String u) {
        super(parent, modal);
        initComponents();
        user = u;
        addUser.setVisible(false);
        removeUser.setVisible(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        settingsLabel = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        updateEmail = new javax.swing.JButton();
        updatePassword = new javax.swing.JButton();
        addUser = new javax.swing.JButton();
        removeUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        mainPanel.setLayout(new java.awt.BorderLayout(0, 20));

        topPanel.setLayout(new javax.swing.BoxLayout(topPanel, javax.swing.BoxLayout.LINE_AXIS));

        settingsLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        settingsLabel.setText("My Settings");
        settingsLabel.setToolTipText("");
        settingsLabel.setIconTextGap(10);
        topPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        topPanel.add(settingsLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abdebatepro/wrench.png"))); // NOI18N
        topPanel.add(icon);

        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        updateEmail.setText("Update Email");
        updateEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmailActionPerformed(evt);
            }
        });

        updatePassword.setText("Update Password");
        updatePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePasswordActionPerformed(evt);
            }
        });

        addUser.setText("Add Referee");
        addUser.setActionCommand("Add Referee");
        addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserActionPerformed(evt);
            }
        });

        removeUser.setText("Remove Referee");
        removeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(updatePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updatePassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeUser)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        mainPanel.add(bottomPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void updateEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmailActionPerformed
        JTextField userInput = new JTextField(10);
        JPasswordField currentPasswordInput = new JPasswordField(10);
        JTextField emailInput = new JTextField(10);
        JPanel myPanel = new JPanel();
        GridLayout layout = new GridLayout(3, 2, 4, 4);
        myPanel.add(new JLabel("Username:"));
        myPanel.add(userInput);
        userInput.setText(user);
        userInput.setEnabled(false);
        myPanel.add(new JLabel("Password:"));
        myPanel.add(currentPasswordInput);
        myPanel.add(new JLabel("New Email:"));
        myPanel.add(emailInput);
        myPanel.setLayout(layout);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Password Reset", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            currentPassword = currentPasswordInput.getText();
            Email = emailInput.getText();
            if (currentPassword.equals(LoginPage.storePass)) {
                try {
                    setupDB();
                    String sql = "Update Logins set Email = ? where Username = ? AND Password = ? ";
                    pstmt = c1.prepareStatement(sql);
                    pstmt.setString(1, Email);
                    pstmt.setString(2, user);
                    pstmt.setString(3, currentPassword);
                    System.out.println(sql);
                    int k = pstmt.executeUpdate();
                    if (k == 1) {
                        System.out.println("Email has been updated");
                    } else {
                        System.out.println("Incorrect password or Incorrect username");
                    }
                    pstmt.close();
                    c1.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username and password don't match, try again");
                updateEmail.doClick();
            }
        }

    }//GEN-LAST:event_updateEmailActionPerformed

    private void updatePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePasswordActionPerformed
        JTextField userInput = new JTextField(10);
        userInput.setEnabled(false);
        JPasswordField currentPasswordInput = new JPasswordField(10);
        JPasswordField NewPasswordInput = new JPasswordField(10);
        JPasswordField NewPasswordConfirmInput = new JPasswordField(10);
        JPanel myPanel = new JPanel();
        GridLayout layout = new GridLayout(4, 2, 4, 4);
        myPanel.add(new JLabel("Username:"));
        myPanel.add(userInput);
        userInput.setText(user);
        myPanel.add(new JLabel("Current Password:"));
        myPanel.add(currentPasswordInput);
        myPanel.add(new JLabel("New Password:"));
        myPanel.add(NewPasswordInput);
        myPanel.add(new JLabel("Confirm New Password:"));
        myPanel.add(NewPasswordConfirmInput);
        myPanel.setLayout(layout);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Password Reset", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            userInput.setText(user);
            currentPassword = currentPasswordInput.getText();
            newPassword = NewPasswordInput.getText();
            passwordConfirmed = NewPasswordConfirmInput.getText();
            if (currentPassword.equals(LoginPage.storePass)) {
                if (newPassword.equals(passwordConfirmed)) {
                    try {
                        setupDB();
                        String sql = "Update Logins set Password = ? where Username = ?";
                        pstmt = c1.prepareStatement(sql);
                        pstmt.setString(1, newPassword);
                        pstmt.setString(2, user);
                        System.out.println(sql);
                        int k = pstmt.executeUpdate();
                        if (k == 1) {
                            JOptionPane.showMessageDialog(null, "Your password has been updated!");
                            System.out.println("Password has been updated");
                        } else {
                            System.out.println("Incorrect password or new password and confirmation password don't match");
                        }
                        pstmt.close();
                        c1.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "New password and confirmed password don't match, try again");
                    updatePassword.doClick();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Your current Password is incorrect, try again");
                updatePassword.doClick();
            }
        }
    }//GEN-LAST:event_updatePasswordActionPerformed

    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserActionPerformed
        JTextField userInput = new JTextField(10);
        JTextField emailInput = new JTextField(10);
        JPasswordField temporaryPasswordInput = new JPasswordField(10);
        JPasswordField PasswordConfirmInput = new JPasswordField(10);
        JRadioButton superReferee = new JRadioButton();
        JRadioButton referee = new JRadioButton();
        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(superReferee);
        bGroup.add(referee);
        String title = "";
        JPanel myPanel = new JPanel();
        GridLayout layout = new GridLayout(7, 0, 0, 0);
        myPanel.add(new JLabel("Username:"));
        myPanel.add(userInput);
        myPanel.add(new JLabel("E-mail:"));
        myPanel.add(emailInput);
        myPanel.add(new JLabel("Temporary Password:"));
        myPanel.add(temporaryPasswordInput);
        myPanel.add(new JLabel("Confirm Password:"));
        myPanel.add(PasswordConfirmInput);
        myPanel.add(new JLabel("Super Referee"));
        myPanel.add(superReferee);
        myPanel.add(new JLabel("Referee"));
        myPanel.add(referee);
        myPanel.setLayout(layout);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Account Management", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nUser = userInput.getText();
            String email = emailInput.getText();
            String temporaryPass = temporaryPasswordInput.getText();
            String confirmPass = PasswordConfirmInput.getText();
            if (temporaryPass.equals(confirmPass)
                    && !temporaryPass.isEmpty() && !confirmPass.isEmpty()) {
                if (emailInput.getText().contains("@") && (emailInput.getText().contains(".com") || emailInput.getText().contains(".net")))  {
                    Logins l1 = new Logins();
                    if (superReferee.isSelected()) {
                        title = "Super Referee";
                    } else if (referee.isSelected()) {
                        title = "Referee";
                    } else {
                        JOptionPane.showMessageDialog(null, "Referee status is not selected");
                    }
                    if (superReferee.isSelected() || referee.isSelected()) {
                        l1.insertDB(nUser, confirmPass, title, email);
                        l1.selectDB();
                        JOptionPane.showMessageDialog(null, "Insert Successfull");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insert Failed");
                        addUser.doClick();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please provide a valid email address!");
                    addUser.doClick();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Insert Failed: Confirm password and temporary password is incorrect");
                addUser.doClick();
            }
        }

    }//GEN-LAST:event_addUserActionPerformed

    private void removeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeUserActionPerformed
        RemoveUser ru = new RemoveUser(ABDebatePro.ab, true);
        ru.setVisible(true);
    }//GEN-LAST:event_removeUserActionPerformed
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
            stmt = c1.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                Settings dialog = new Settings(new javax.swing.JFrame(), true, "11");
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//
//                dialog.setVisible(true);
//
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUser;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton removeUser;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JButton updateEmail;
    private javax.swing.JButton updatePassword;
    // End of variables declaration//GEN-END:variables
}
