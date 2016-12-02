package abdebatepro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
//import java.util.Date;
import java.util.Optional;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class DatePick extends javax.swing.JDialog {

    JFXPanel panel;
    Scene scene;
    StackPane stack;
    Text dateLabel;
    Button okayButton;
    Button cancelButton;
    DatePicker datePicker;
    Connection c1;

    //========================== Constructor For StartDate ===================//
    public DatePick(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        panel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stack = new StackPane();
                scene = new Scene(stack, 0, 0);

                dateLabel = new Text("Please choose start date:");
                datePicker = new DatePicker();
                okayButton = new Button("Insert");
                cancelButton = new Button("Cancel");

                dateLabel.setTranslateX(-120);
                dateLabel.setTranslateY(-50);
                datePicker.setTranslateX(100);
                datePicker.setTranslateY(-50);
                okayButton.setTranslateX(100);
                okayButton.setTranslateY(40);
                cancelButton.setTranslateX(180);
                cancelButton.setTranslateY(40);
                okayButton.setOnAction((event) -> {
                    okayButtonMethod();
                });
                cancelButton.setOnAction((event) -> {
                    cancelButtonMethod();
                });
                panel.setScene(scene);
                stack.getChildren().add(dateLabel);
                stack.getChildren().add(datePicker);
                stack.getChildren().add(okayButton);
                stack.getChildren().add(cancelButton);
                disableDays();
                getDate();
            }
        });

        this.getContentPane().add(panel);
        this.setSize(460, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Set Start Date");
        this.setVisible(true);
    }

    //========================== Constructor For MatchDate ===================//
    public DatePick(java.awt.Frame parent, boolean modal, String a) {
        super(parent, modal);
        panel = new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stack = new StackPane();
                scene = new Scene(stack, 0, 0);

                dateLabel = new Text("Please choose match date:");
                datePicker = new DatePicker();
                okayButton = new Button("Insert");
                cancelButton = new Button("Cancel");

                dateLabel.setTranslateX(-120);
                dateLabel.setTranslateY(-50);
                datePicker.setTranslateX(100);
                datePicker.setTranslateY(-50);
                okayButton.setTranslateX(100);
                okayButton.setTranslateY(40);
                cancelButton.setTranslateX(180);
                cancelButton.setTranslateY(40);
                okayButton.setOnAction((event) -> {
                    okayButtonMatchMethod();
                });
                cancelButton.setOnAction((event) -> {
                    cancelButtonMethod();
                });
                panel.setScene(scene);
                stack.getChildren().add(dateLabel);
                stack.getChildren().add(datePicker);
                stack.getChildren().add(okayButton);
                stack.getChildren().add(cancelButton);
                disableDaysForMatchDate();
                getDate();
            }
        });

        this.getContentPane().add(panel);
        this.setSize(460, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Set Match Date");
        this.setVisible(true);
    }

    //========================== SetupDB =====================================//
    public void setupDB() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            c1 = DriverManager.getConnection(ABDebatePro.DBURL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //okay button for set match Date//
    public void matchDateOkayButton(){
        Schedule s1 = new Schedule();
        
        s1.updateMatchDate(java.sql.Date.valueOf(datePicker.getValue()), 1);
    }
    public void okayButtonMethod() {
        //Date dTemp = new Date();
        //LocalDate localDate = datePicker.getValue();
        //Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        //dTemp = Date.from(instant);
        try {
            //ABDebatePro.startDate = new java.sql.Date(dTemp.getTime());
            ABDebatePro.startDate = java.sql.Date.valueOf(datePicker.getValue());
            Dates ds = new Dates();
            setupDB();
            String SQL = "SELECT * FROM Dates";
            PreparedStatement ps = c1.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date d1 = rs.getDate("StartDate");
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Update Confirmation");
                alert.setContentText("Start Date already exists in the database, would you like to update it?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ds.updateDB(ABDebatePro.startDate);
                    this.setVisible(false);
                }
            } else {
                ds.insertDB(ABDebatePro.startDate);
                this.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void okayButtonMatchMethod() {
        this.setVisible(false);
    }

    public void cancelButtonMethod() {
        this.setVisible(false);
    }

    //========================== Gets Date ===================================//
    public void getDate() {
        try {
            Dates ds = new Dates();
            ds.selectDB();
            datePicker.setValue(ds.sDate.toLocalDate());
            okayButton.setText("Update");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void disableDays() {
        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override

            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() != DayOfWeek.SATURDAY);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
    }

    public void disableDaysForMatchDate() {
        datePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() != DayOfWeek.SATURDAY);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
                for (LocalDate d : Schedule.datesList) {
                    if (item.equals(d)) {
                        setDisable(true);
                    }
                }

            }
        });
    }
}
