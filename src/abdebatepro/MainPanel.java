package abdebatepro;
//========================== Imports =========================================//
import static abdebatepro.ABDebatePro.DBURL;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
public class MainPanel extends javax.swing.JPanel {
    //========================== Declarations ==================================
    public String user, pass;
    public Connection c1;
    public Statement stmt;
    private PreparedStatement pstmt;
    ResultSet rs;
    String someUser, somePass;
    public String sqlStatement = "select * from Schedule Order by MatchNumber ASC";
    Algorithm a = new Algorithm();
    public int noOfWeeks;
    //========================== Constructor ===================================
    public MainPanel() {
        initComponents();
        setupDB();
        bottomPanel.setVisible(false);
        settingsButton.setVisible(false);
        tieScoreButton.setVisible(false);
        setCellsCentered();
        populateChangeRefBox();
    }
    public void disableAssignedRef() {
        schTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Match Number", "First Team", "Second Team", "First Team Score", "Second Team Score", "Date", "Time", "Assigned Referee"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Refresh();
        populateChangeRefBox();
        setCellsCentered();
    }
    public void enableAssignedRef() {
        schTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Match Number", "First Team", "Second Team", "First Team Score", "Second Team Score", "Date", "Time", "Assigned Referee"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, true
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Refresh();
        populateChangeRefBox();
        setCellsCentered();
    }
    public void setCellsCentered() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        TableModel tableModel = schTable.getModel();
        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            schTable.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
        }
        renderer = (DefaultTableCellRenderer) schTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rendererTS = new DefaultTableCellRenderer();
        rendererTS.setHorizontalAlignment(JLabel.CENTER);
        TableModel tableModelTS = teamTable.getModel();
        for (int columnIndex = 0; columnIndex < tableModelTS.getColumnCount(); columnIndex++) {
            teamTable.getColumnModel().getColumn(columnIndex).setCellRenderer(rendererTS);
        }
        rendererTS = (DefaultTableCellRenderer) teamTable.getTableHeader().getDefaultRenderer();
        rendererTS.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
        renderer1.setHorizontalAlignment(JLabel.CENTER);
        TableModel tableModel1 = rescheduleTable.getModel();
        for (int columnIndex = 0; columnIndex < tableModel1.getColumnCount(); columnIndex++) {
            rescheduleTable.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer1);
        }
        renderer1 = (DefaultTableCellRenderer) rescheduleTable.getTableHeader().getDefaultRenderer();
        renderer1.setHorizontalAlignment(JLabel.CENTER);
    }
    public void populateChangeRefBox() {
        setupDB();
        TableColumn priv = schTable.getColumnModel().getColumn(7);
        JComboBox comboBox = new JComboBox();
        comboBox.addActionListener((ActionEvent e) -> {
            insertAssignedRef();
        });
        try {
            String sql = "SELECT * from Logins where Privilege = 'Referee'";
            pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.addItem(rs.getString("Username"));
            }
            c1.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        priv.setCellEditor(new DefaultCellEditor(comboBox));
    }
    public void insertAssignedRef() {
        if (!(schTable.getSelectedRow() == -1)) {
            //checking row and column
            String row = Integer.toString(schTable.getSelectedRow());
            String column = Integer.toString(schTable.getSelectedColumn());
            System.out.println("Row: " + row + " " + "Column: " + column);
            //adding
            int matchNumber = Integer.parseInt((String) schTable.getValueAt(schTable.getSelectedRow(), 0));
            String assignedRef = (String) schTable.getValueAt(schTable.getSelectedRow(), 7);
            setupDB();
            try {
                String sql = "UPDATE Schedule SET AssignedReferee = ? where MatchNumber = ?";
                System.out.println(sql);
                PreparedStatement pstmt = c1.prepareStatement(sql);
                pstmt.setString(1, assignedRef);
                pstmt.setInt(2, matchNumber);
                int z = pstmt.executeUpdate();;
                if (z == 1) {
                    System.out.println("Update Ref successful");
                } else {
                    System.out.println("Update Ref Failed");
                }
                pstmt.close();
                c1.close();
                Refresh();
            } catch (Exception fe) {
                System.out.println(fe);
            }
            System.out.println("Match Number: " + matchNumber + "Privilege: " + assignedRef);
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

        topPanel = new javax.swing.JPanel();
        leftSidePanel = new javax.swing.JPanel();
        topLabel = new javax.swing.JLabel();
        weekBox = new javax.swing.JComboBox<>();
        rightSidePanel = new javax.swing.JPanel();
        adminPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        loginLabel = new javax.swing.JLabel();
        privLabel = new javax.swing.JLabel();
        midPanel = new javax.swing.JPanel();
        totalGames = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        schScrollPane = new javax.swing.JScrollPane();
        schTable = new javax.swing.JTable();
        teamScrollPane = new javax.swing.JScrollPane();
        teamTable = new javax.swing.JTable();
        reschedulePane = new javax.swing.JScrollPane();
        rescheduleTable = new javax.swing.JTable();
        bottomPanel = new javax.swing.JPanel();
        superPanel = new javax.swing.JPanel();
        startDateButton = new javax.swing.JButton();
        editTeam = new javax.swing.JButton();
        changeMatchDate = new javax.swing.JButton();
        matchButton = new javax.swing.JButton();
        tieButton = new javax.swing.JButton();
        scoreButton = new javax.swing.JButton();
        tieScoreButton = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setLayout(new java.awt.BorderLayout());

        topPanel.setPreferredSize(new java.awt.Dimension(322, 80));

        leftSidePanel.setLayout(new javax.swing.BoxLayout(leftSidePanel, javax.swing.BoxLayout.Y_AXIS));

        topLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        topLabel.setText("High School Debate Calendar");
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftSidePanel.add(topLabel);
        leftSidePanel.add(Box.createRigidArea(new Dimension(0, 5)));

        weekBox.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        weekBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        weekBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekBoxActionPerformed(evt);
            }
        });
        leftSidePanel.add(weekBox);

        rightSidePanel.setLayout(new javax.swing.BoxLayout(rightSidePanel, javax.swing.BoxLayout.Y_AXIS));

        adminPanel.setLayout(new javax.swing.BoxLayout(adminPanel, javax.swing.BoxLayout.X_AXIS));
        adminPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abdebatepro/wrench.png"))); // NOI18N
        settingsButton.setMaximumSize(new java.awt.Dimension(30, 30));
        settingsButton.setMinimumSize(new java.awt.Dimension(30, 30));
        settingsButton.setPreferredSize(new java.awt.Dimension(30, 30));
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        adminPanel.add(settingsButton);
        adminPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        loginButton.setText("Admin Login");
        loginButton.setMaximumSize(new java.awt.Dimension(103, 30));
        loginButton.setMinimumSize(new java.awt.Dimension(103, 30));
        loginButton.setPreferredSize(new java.awt.Dimension(103, 30));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        adminPanel.add(loginButton);

        rightSidePanel.add(adminPanel);

        loginLabel.setText("   ");
        loginLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightSidePanel.add(loginLabel);

        privLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightSidePanel.add(privLabel);

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 877, Short.MAX_VALUE)
                .addComponent(rightSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addComponent(leftSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addComponent(rightSidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        add(topPanel, java.awt.BorderLayout.PAGE_START);

        totalGames.setText("Total Games: 0");

        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsStateChanged(evt);
            }
        });

        schTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Match Number", "First Team", "Second Team", "First Team Score", "Second Team Score", "Date", "Time", "Assigned Referee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        schTable.setRowHeight(50);
        schTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        schScrollPane.setViewportView(schTable);

        tabs.addTab("Schedule", schScrollPane);

        teamTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        teamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Team Name", "Score"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        teamTable.setAutoscrolls(false);
        teamTable.setRowHeight(50);
        teamScrollPane.setViewportView(teamTable);

        tabs.addTab("Total Score", teamScrollPane);

        rescheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Match Number", "First Team", "Second Team", "First Team Score", "Second Team Score", "Date", "Time", "Assigned Referee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rescheduleTable.setRowHeight(50);
        reschedulePane.setViewportView(rescheduleTable);

        tabs.addTab("Tie Schedule", reschedulePane);

        javax.swing.GroupLayout midPanelLayout = new javax.swing.GroupLayout(midPanel);
        midPanel.setLayout(midPanelLayout);
        midPanelLayout.setHorizontalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(midPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(midPanelLayout.createSequentialGroup()
                        .addComponent(totalGames)
                        .addGap(0, 1168, Short.MAX_VALUE)))
                .addContainerGap())
        );
        midPanelLayout.setVerticalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(midPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalGames)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE))
        );

        add(midPanel, java.awt.BorderLayout.CENTER);

        bottomPanel.setPreferredSize(new java.awt.Dimension(423, 48));

        superPanel.setLayout(new javax.swing.BoxLayout(superPanel, javax.swing.BoxLayout.X_AXIS));

        startDateButton.setText("Set Start Date");
        startDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDateButtonActionPerformed(evt);
            }
        });
        superPanel.add(startDateButton);
        superPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        editTeam.setText("Setup Teams");
        editTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTeamActionPerformed(evt);
            }
        });
        superPanel.add(editTeam);
        superPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        changeMatchDate.setText("Change Match Date");
        changeMatchDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeMatchDateActionPerformed(evt);
            }
        });
        superPanel.add(changeMatchDate);
        superPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        matchButton.setText("Create Schedule");
        matchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchButtonActionPerformed(evt);
            }
        });
        superPanel.add(matchButton);
        superPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        tieButton.setText("Create Tie Schedule");
        tieButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tieButtonActionPerformed(evt);
            }
        });
        superPanel.add(tieButton);

        scoreButton.setText("Edit Match Score");
        scoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreButtonActionPerformed(evt);
            }
        });

        tieScoreButton.setText("Edit Tie Score");
        tieScoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tieScoreButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(superPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addComponent(tieScoreButton)
                .addGap(18, 18, 18)
                .addComponent(scoreButton)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(superPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(scoreButton)
                        .addComponent(tieScoreButton)))
                .addGap(682, 682, 682))
        );

        add(bottomPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    //========================== DB Setup=======================================
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
            stmt = c1.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void populateDropBox() {
        a.count();
        if (a.numberOfTeams == 2) {
            noOfWeeks = 1;
        }
        if (a.numberOfTeams == 3) {
            noOfWeeks = 3;
        }
        if (a.numberOfTeams == 4) {
            noOfWeeks = 6;
        }
        if (a.numberOfTeams >= 5) {
            noOfWeeks = 10;
        }
        String[] weekBoxString = new String[noOfWeeks + 1];
        weekBoxString[0] = "All Weeks";
        for (int i = 0; i < noOfWeeks; i++) {
            weekBoxString[i + 1] = "Week " + (i + 1);
        }
        weekBox.setModel(new javax.swing.DefaultComboBoxModel<>(weekBoxString));
        weekBox.setSelectedIndex(0);
        loginButton.requestFocusInWindow();
    }
    //========================== Checks Teams From Database ====================
    public void checkTeams() {
        try {
            int count = 0;
            String sql = "SELECT count(*) AS total from Teams";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "No teams are added to the database, please add some teams.", "Error!" + "", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Refresh() {
        try {
            Connection c1 = DriverManager.getConnection(DBURL);
            PreparedStatement pstmt = c1.prepareStatement(sqlStatement);
            ResultSet rs = pstmt.executeQuery();
            DefaultTableModel yourModel = (DefaultTableModel) schTable.getModel();
            yourModel.setRowCount(0);
            while (rs.next()) {
                yourModel = (DefaultTableModel) schTable.getModel();
                yourModel.addRow(new Object[]{rs.getString("MatchNumber"), rs.getString("FirstTeam"), rs.getString("SecondTeam"),
                    rs.getInt("FirstTeamScore"), rs.getInt("SecondTeamScore"), rs.getDate("MatchDate"), rs.getString("Time"), rs.getString("AssignedReferee")});
            }
            c1.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error was here:" + e);
        }
        try {
            Connection c1 = DriverManager.getConnection(DBURL);
            Statement stmt = c1.createStatement();
            ResultSet rs = stmt.executeQuery("select Count(MatchNumber) AS TotalNumber from Schedule");
            while (rs.next()) {
                int number = rs.getInt("TotalNumber");
                totalGames.setText("Total Games: " + Integer.toString(number));
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //populateDropBox();
    }    //========================== Prompts To Reenter Password ===================
    public void promptPassword() {
        LoginPage lp = new LoginPage(ABDebatePro.ab, true, "Call second constructor");
        lp.setVisible(true);
    }
    private void matchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchButtonActionPerformed
        Teams t1 = new Teams();
        t1.countTeams();
        Schedule s1 = new Schedule();
        if (t1.totalTeams < 2) {    
            JOptionPane.showMessageDialog(null, "Please add some teams!");
        } else {
            if (schTable.getRowCount() > 0) {
                JOptionPane.showMessageDialog(null, "Warning, previous Schedule would be replaced!");
                promptPassword();
                if (LoginPage.accessGrant == true) {
                    a.count();
                    a.makeSch();
                    a.insertSch();
                    populateDropBox();
                    s1.selectReferee();
                    Refresh();
                    TieSchedule ts = new TieSchedule();
                    ts.deleteTieSchedule();
                }
            } else {
                a.count();
                a.makeSch();
                a.insertSch();
                populateDropBox();
                s1.selectReferee();
                Refresh();
                TieSchedule ts = new TieSchedule();
                ts.deleteTieSchedule();
            }
        }
    }//GEN-LAST:event_matchButtonActionPerformed

    private void scoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreButtonActionPerformed
        if (!(schTable.getSelectedRow() == -1)) {
            int scoreOne, scoreTwo;
            scoreOne = (Integer) schTable.getValueAt(schTable.getSelectedRow(), 3);
            scoreTwo = (Integer) schTable.getValueAt(schTable.getSelectedRow(), 4);
            String assignedRef = (String) schTable.getValueAt(schTable.getSelectedRow(), 7);
            if ((scoreOne == 0 && scoreTwo == 0 && LoginPage.storeUser.equals(assignedRef)) || privLabel.getText().equals("Super Referee")) {
                JTextField scoreField = new JTextField(10);
                JTextField scoreField2 = new JTextField(10);
                JPanel myPanel = new JPanel();
                GridLayout layout = new GridLayout(2, 2, 5, 5);
                // two j lables for the edit score button
                myPanel.add(new JLabel("Team " + schTable.getValueAt(schTable.getSelectedRow(), 1) + " Score:"));
                myPanel.add(scoreField);
                myPanel.setLayout(layout);
                myPanel.add(new JLabel("Team " + schTable.getValueAt(schTable.getSelectedRow(), 2) + " Score:"));
                myPanel.add(scoreField2);
                myPanel.setLayout(layout);
                String regex = "[0-9]+";
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Edit Score", JOptionPane.OK_CANCEL_OPTION);
                String score1 = scoreField.getText();
                String score2 = scoreField2.getText();
                if (result == JOptionPane.OK_OPTION) {
                    if (score1.matches(regex) && score2.matches(regex)) {
                        if (Integer.parseInt(score1) >= 0 && Integer.parseInt(score1) < 11 && Integer.parseInt(score2) >= 0 && Integer.parseInt(score2) < 11) {
                            try {
                                Schedule sch = new Schedule();
                                int matchNumber = Integer.parseInt((String) schTable.getValueAt(schTable.getSelectedRow(), 0));
                                sch.selectDB(matchNumber);
                                sch.updateScore(sch.getMatchNumber(), Integer.parseInt(scoreField.getText()), Integer.parseInt(scoreField2.getText()));
                            } catch (Exception e) {
                                System.out.println(e);
                            } finally {
                                Refresh();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Score should be between 0 and 10!");
                            scoreButton.doClick();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter a valid score between 0 and 10!");
                        scoreButton.doClick();
                    }
                }//no else
            } else {
                JOptionPane.showMessageDialog(null, "Not authorized to change scores!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need to select a match first!");
        }
    }//GEN-LAST:event_scoreButtonActionPerformed

    private void editTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTeamActionPerformed
        EditTeams et = new EditTeams(ABDebatePro.ab, true);
        et.setVisible(true);
        Refresh();
    }//GEN-LAST:event_editTeamActionPerformed

    private void startDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDateButtonActionPerformed
        DatePick dp = new DatePick(ABDebatePro.ab, true);
    }//GEN-LAST:event_startDateButtonActionPerformed

    private void tabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsStateChanged
        if (tabs.getSelectedIndex() == 1) {
            try {
                Teams t1 = new Teams();
                t1.CalculateTeamScore();
                bottomPanel.setVisible(false);
                Connection c1 = DriverManager.getConnection(DBURL);
                PreparedStatement pstmt = c1.prepareStatement("select * from Teams Order by TeamScore DESC");
                ResultSet rs = pstmt.executeQuery();
                DefaultTableModel yourModel = (DefaultTableModel) teamTable.getModel();
                yourModel.setRowCount(0);
                while (rs.next()) {
                    yourModel = (DefaultTableModel) teamTable.getModel();
                    yourModel.addRow(new Object[]{rs.getString("TeamName"), rs.getInt("TeamScore")});
                }
                c1.close();
                pstmt.close();
            } catch (Exception e) {
                System.out.println("Error was here:" + e);
            }
        } else if (tabs.getSelectedIndex() == 2) {
            refreshTie();
            if (privLabel.getText().equals("")) {
                
            } else {
            bottomPanel.setVisible(true);
            tieScoreButton.setVisible(true);
            scoreButton.setVisible(false);
            startDateButton.setVisible(false);
            editTeam.setVisible(false);
            changeMatchDate.setVisible(false);
            matchButton.setVisible(false);
            tieButton.setVisible(false);
            }
        } else if (tabs.getSelectedIndex() == 0) {
            if (privLabel.getText().equals("")) {
                
            } else {
            bottomPanel.setVisible(true);
            tieScoreButton.setVisible(false);
            scoreButton.setVisible(true);
            startDateButton.setVisible(true);
            editTeam.setVisible(true);
            changeMatchDate.setVisible(true);
            matchButton.setVisible(true);
            tieButton.setVisible(true);
            }
        }
    }//GEN-LAST:event_tabsStateChanged
    public void refreshTie() {
        try {
            Connection c1 = DriverManager.getConnection(DBURL);
            PreparedStatement pstmt = c1.prepareStatement("select * from TieSchedule Order by MatchNumber ASC");
            ResultSet rs = pstmt.executeQuery();
            DefaultTableModel yourModel = (DefaultTableModel) rescheduleTable.getModel();
            yourModel.setRowCount(0);
            while (rs.next()) {
                yourModel = (DefaultTableModel) rescheduleTable.getModel();
                yourModel.addRow(new Object[]{rs.getString("MatchNumber"), rs.getString("FirstTeam"), rs.getString("SecondTeam"),
                    rs.getInt("FirstTeamScore"), rs.getInt("SecondTeamScore"), rs.getDate("MatchDate"), rs.getString("Time"), rs.getString("AssignedReferee")});
            }
            c1.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error was here:" + e);
        }
    }
    private void weekBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekBoxActionPerformed
        Dates d1 = new Dates();
        d1.selectDB();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1.sDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //select date from datesch where "somedate" = schdate
        if (weekBox.getSelectedItem().equals("All Weeks")) {
            sqlStatement = "select * from Schedule Order by MatchNumber ASC";
        } else if (weekBox.getSelectedItem().equals("Week 1")) {
            sqlStatement = "select * from Schedule where MatchDate = #" + d1.sDate + "# Order by MatchNumber ASC";
        } else {
            for (int i = 0; i < noOfWeeks; i++) {
                if (weekBox.getSelectedItem().equals("Week " + (i + 2))) {
                    cal.add(Calendar.DATE, 7 * (i + 1));
                    sqlStatement = "select * from Schedule where MatchDate = #" + dateFormat.format(cal.getTime()) + "# Order by MatchNumber ASC";
                    //weekBox.setSelectedIndex(weekBox.getSelectedItem().);
                }
            }
        }
        System.out.println("You chose: " + weekBox.getSelectedItem());
        Refresh();
    }//GEN-LAST:event_weekBoxActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        if (loginButton.getText().equals("Admin Login")) {
            LoginPage lp = new LoginPage(ABDebatePro.ab, true);
            lp.setVisible(true);
            if (privLabel.getText().equals("Super Referee")) {
                enableAssignedRef();
            } else if (privLabel.getText().equals("Referee")) {
                disableAssignedRef();
            }
        } else if (loginButton.getText().equals("Logout")) {
            bottomPanel.setVisible(false);
            loginButton.setText("Admin Login");
            loginLabel.setText("");
            privLabel.setText("");
            LoginPage.storeUser = "";
            LoginPage.storePass = "";
            settingsButton.setVisible(false);
            disableAssignedRef();
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void changeMatchDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeMatchDateActionPerformed
        if (!(schTable.getSelectedRow() == -1)) {
            String n1 = (String) schTable.getModel().getValueAt(schTable.getSelectedRow(), 1);
            String n2 = (String) schTable.getModel().getValueAt(schTable.getSelectedRow(), 2);
            String mn = (String) schTable.getModel().getValueAt(schTable.getSelectedRow(), 0);
            Date date = (Date) schTable.getModel().getValueAt(schTable.getSelectedRow(), 5);
            int matchNumber = Integer.parseInt(mn);
            Schedule s1 = new Schedule();
            Schedule.datesList.clear();
            s1.storeDate(n1, n2);
            s1.storeDate2(n1, n2);
            DatePick dp = new DatePick(ABDebatePro.ab, true, matchNumber, date);
        } else {
            JOptionPane.showMessageDialog(null, "You need to select a match first!");
        }
        Refresh();
    }//GEN-LAST:event_changeMatchDateActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        if (privLabel.getText().equals("Super Referee")) {
            Settings s1 = new Settings(ABDebatePro.ab, true, LoginPage.storeUser, "Super");
            s1.setVisible(true);
        } else {
            Settings s1 = new Settings(ABDebatePro.ab, true, LoginPage.storeUser);
            s1.setVisible(true);
        }
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void tieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tieButtonActionPerformed
        try {
                Teams t1 = new Teams();
                t1.CalculateTeamScore();
                Connection c1 = DriverManager.getConnection(DBURL);
                PreparedStatement pstmt = c1.prepareStatement("select * from Teams Order by TeamScore DESC");
                ResultSet rs = pstmt.executeQuery();
                DefaultTableModel yourModel = (DefaultTableModel) teamTable.getModel();
                yourModel.setRowCount(0);
                while (rs.next()) {
                    yourModel = (DefaultTableModel) teamTable.getModel();
                    yourModel.addRow(new Object[]{rs.getString("TeamName"), rs.getInt("TeamScore")});
                }
                c1.close();
                pstmt.close();
            } catch (Exception e) {
                System.out.println("Error was here:" + e);
            }
        TieSchedule ts1 = new TieSchedule();
        ts1.tie();
        ts1.selectReferee();
    }//GEN-LAST:event_tieButtonActionPerformed

    private void tieScoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tieScoreButtonActionPerformed
         if (!(rescheduleTable.getSelectedRow() == -1)) {
            int scoreOne, scoreTwo;
            scoreOne = (Integer) rescheduleTable.getValueAt(rescheduleTable.getSelectedRow(), 3);
            scoreTwo = (Integer) rescheduleTable.getValueAt(rescheduleTable.getSelectedRow(), 4);
            String assignedRef = (String) rescheduleTable.getValueAt(rescheduleTable.getSelectedRow(), 7);
            if ((scoreOne == 0 && scoreTwo == 0 && LoginPage.storeUser.equals(assignedRef)) || privLabel.getText().equals("Super Referee")) {
                JTextField scoreField = new JTextField(10);
                JTextField scoreField2 = new JTextField(10);
                JPanel myPanel = new JPanel();
                GridLayout layout = new GridLayout(2, 2, 5, 5);
                // two j lables for the edit score button
                myPanel.add(new JLabel("Team " + rescheduleTable.getValueAt(rescheduleTable.getSelectedRow(), 1) + " Score:"));
                myPanel.add(scoreField);
                myPanel.setLayout(layout);
                myPanel.add(new JLabel("Team " + rescheduleTable.getValueAt(rescheduleTable.getSelectedRow(), 2) + " Score:"));
                myPanel.add(scoreField2);
                myPanel.setLayout(layout);
                String regex = "[0-9]+";
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Edit Score", JOptionPane.OK_CANCEL_OPTION);
                String score1 = scoreField.getText();
                String score2 = scoreField2.getText();
                if (result == JOptionPane.OK_OPTION) {
                    if (score1.matches(regex) && score2.matches(regex)) {
                        if (Integer.parseInt(score1) >= 0 && Integer.parseInt(score1) < 11 && Integer.parseInt(score2) >= 0 && Integer.parseInt(score2) < 11) {
                            try {
                                 TieSchedule tsch = new TieSchedule();
                                System.out.println(rescheduleTable.getSelectedRow() + 1);
                                tsch.selectDB(rescheduleTable.getSelectedRow() + 1);
                                tsch.updateScore(tsch.getMatchNumber(), Integer.parseInt(scoreField.getText()), Integer.parseInt(scoreField2.getText()));
                                Teams t1 = new Teams();
                                t1.CalculateTeamScore();
                            } catch (Exception e) {
                                System.out.println(e);
                            } finally {
                                refreshTie();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Score should be between 0 and 10!");
                            tieScoreButton.doClick();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter a valid score between 0 and 10!");
                        tieScoreButton.doClick();
                    }
                }//no else
            } else {
                JOptionPane.showMessageDialog(null, "Not authorized to change scores!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You need to select a match first!");
        }
    }//GEN-LAST:event_tieScoreButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel adminPanel;
    public static javax.swing.JPanel bottomPanel;
    public static javax.swing.JButton changeMatchDate;
    public static javax.swing.JButton editTeam;
    public static javax.swing.JPanel leftSidePanel;
    public static javax.swing.JButton loginButton;
    public static javax.swing.JLabel loginLabel;
    public static javax.swing.JButton matchButton;
    public static javax.swing.JPanel midPanel;
    public static javax.swing.JLabel privLabel;
    public static javax.swing.JScrollPane reschedulePane;
    public static javax.swing.JTable rescheduleTable;
    public javax.swing.JPanel rightSidePanel;
    public static javax.swing.JScrollPane schScrollPane;
    public static javax.swing.JTable schTable;
    public static javax.swing.JButton scoreButton;
    public static javax.swing.JButton settingsButton;
    public static javax.swing.JButton startDateButton;
    public static javax.swing.JPanel superPanel;
    public static javax.swing.JTabbedPane tabs;
    public static javax.swing.JScrollPane teamScrollPane;
    public javax.swing.JTable teamTable;
    public static javax.swing.JButton tieButton;
    public static javax.swing.JButton tieScoreButton;
    public javax.swing.JLabel topLabel;
    public static javax.swing.JPanel topPanel;
    public javax.swing.JLabel totalGames;
    public static javax.swing.JComboBox<String> weekBox;
    // End of variables declaration//GEN-END:variables
}
