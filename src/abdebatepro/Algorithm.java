package abdebatepro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.util.ArrayList;
public class Algorithm {
    //========================== Properties ==================================//
    public int MatchNumber, FirstTeamScore, SecondTeamScore;
    public String FirstTeam, SecondTeam;
    public java.sql.Date MatchDate;
    public Connection c1;
    public Statement stmt;
    ResultSet rs;
    public int count;
    String[][] matchupAPN;
    int matchupNumber;
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
    //========================== Counting Teams ==============================//
    public void count() {
        setupDB();
        try {
            count=0;
            String sql = "SELECT count(*) AS total from Teams";
            PreparedStatement pstmt = c1.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while( rs.next() )
            {
                count=rs.getInt("total");
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "No teams are added to the database, please add some teams.", "Error!" + "", JOptionPane.INFORMATION_MESSAGE);
            }
            c1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void makeSch(){
      setupDB();
      int numberOfTeam = count;
      matchupNumber = (numberOfTeam*(numberOfTeam-1))/2;
      matchupAPN = new String[matchupNumber][3];
      String[] teamName = new String[numberOfTeam];

      Teams t = new Teams();
      t.selectAllDB();
      //input the team names
      for (int i = 0; i < numberOfTeam; i++){
         teamName[i] = t.allTeams.get(i);
         }

      //auto input
      //for (int i = 0; i < numberOfTeam; i++)
         //teamName[i] = Integer.toString(i);


      //Generates matchups and saves in matchup[] array
      //matchupAPN = matchupGenerator(teamName, teamName.length);
      String[][] abb = new String[matchupNumber][6];
      int abbCounter = 0;
      for (int i = 0; i < numberOfTeam-1; i++){
       for(int j = 0; j < numberOfTeam-1-i; j++){
         //System.out.println(j + " " + (i+j+1));
         abb[abbCounter][1] = teamName[j];
         abb[abbCounter][2] = teamName[(i+j+1)];
         abbCounter++;
       }
      }
      String[][] temp = new String[1][6];
      temp[0][0] = abb[0][0];
      abb[0][0] = abb[matchupNumber-1][0];
      abb[matchupNumber-1][0] = temp[0][0];
      temp[0][1] = abb[0][1];
      abb[0][1] = abb[matchupNumber-1][1];
      abb[matchupNumber-1][1] = temp[0][1];
      temp[0][2] = abb[0][2];
      abb[0][2] = abb[matchupNumber-1][2];
      abb[matchupNumber-1][2] = temp[0][2];
     //for (int i = 0; i < 45; i++)
      //System.out.println(abb[i][1]+ " vs " + abb[i][2]);
      for (int i = 0; i < matchupNumber; i++){
         matchupAPN[i][0] = abb[i][1] + " vs " + abb[i][2];
         matchupAPN[i][1] = abb[i][1];
         matchupAPN[i][2] = abb[i][2];
         }
    }
    public void insertSch(){
        Schedule s1 = new Schedule();
        s1.deleteAllDB();
        int matchupConstant = 0;
        //java.sql.Date date1 = java.sql.Date.valueOf("2016-10-27");
        Algorithm.randomization(matchupAPN, count);

        Date[] weekOfGames = new Date[(count*(count-1))/2];
        weekOfGames = getDate(count);
        for (int i = 0; i < matchupNumber; i++){
        s1.insertDB(i+1, matchupAPN[i][1], matchupAPN[i][2], 0, 0, new java.sql.Date(weekOfGames[i].getTime()));
        }
    }

    public Date[] getDate(int numberOfTeams){
        String dt = "";  // Start date
            int numberOfGames = (count*(count-1))/2;
            Date[] dateOfWeeks = new Date[10];
            Date[] weekOfGames = new Date[numberOfGames];
            Dates d = new Dates();


            for (int i=0; i < 70; i+=7){
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Calendar c = Calendar.getInstance();
            d.selectDB();
            
            c.setTime(d.sDate);
            c.add(Calendar.DATE, i);  // increments the number of days by 7
            dt = sdf.format(c.getTime());
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            try {
                //Date startDate1 = df.parse(dt);
                //ABDebatePro.startDate = new java.sql.Date(startDate1.getTime());
                  System.out.println("Week #" + (i/7+1) +": " + d.sDate);
                  System.out.println(df + " " + dt);
                  dateOfWeeks[i/7] = df.parse(dt);
            } catch (Exception e) {
                  System.out.println(e);
            }
            //ABDebatePro.startDate = startDate1;
            //System.out.println(dTemp);
            }

            int counter = 0;
            for (int i = 0; i < numberOfGames%10; i++){
                for (int j = 0; j < numberOfGames/10 + 1; j++){
                    weekOfGames[counter] = dateOfWeeks[i];
                    counter++;
                }
            }
            for (int i = numberOfGames%10; i < 10; i++){
                for (int j = 0; j < numberOfGames/10; j++){
                    weekOfGames[counter] = dateOfWeeks[i];
                    counter++;
                }
            }
            //for (int i = 0; i < numberOfGames; i++){
            //    System.out.println(weekOfGames[i]);
            //}
            return weekOfGames;
    }

     public static String[][] randomization(String[][] array, int noOfTeams){
         int noOfElements = array.length;
         int counter1 = 0;
         int counter2 = 0;
         int counter3 = 0;
         int counter4 = 0;
         int weeklyGames1 = noOfElements/10 + 1;
         int weeklyGames2 = noOfElements/10;
         int[] weekPart = new int[10];
         for (int i = 0; i < noOfElements%10; i++){
             weekPart[i] = weeklyGames1 * i;
         }
         for (int i = noOfElements%10; i < 10; i++){
             int a = noOfElements;
             if (noOfElements%10 == 0 )
                 a = 1;
             weekPart[i] = weeklyGames2 * (i-noOfElements%10+1) + weekPart[a%10-1]+1;
         }
         boolean repeat = false;
         int[] weeklyGames = new int[10];
         int a = noOfElements;
         if (noOfElements == 10)
            a = 1;

         for (int i = 0; i < noOfElements%10+1; i++){
            weeklyGames[i] = i * (noOfElements/10 + 1);
            }
         for (int i = 0; i < 10 - noOfElements%10-1; i++){
             weeklyGames[i+noOfElements%10+1] = (i+1) * (noOfElements/10) + weeklyGames[a%10-1];
             }

         int[] weeklyConstant = new int[10];
      for (int i = 0; i < noOfElements%10; i++)
         weeklyConstant[i] = (i + 1) * (noOfElements/10 + 1);
      for (int i = 0; i < 10 - noOfElements%10; i++)
         weeklyConstant[i+noOfElements%10] = (i+1) * (noOfElements/10) + weeklyConstant[a%10-1]-1;
      //for (int i = 0; i < 10; i++)
         //System.out.println(weeklyConstant[i]);


            do{
            repeat = false;
            for (int i = 0; i < noOfElements; i++){
               int s = i + (int)(Math.random() * (noOfElements - i));
               String[][] temp = new String[1][3];
               temp[0][0] = array[s][0];
               array[s][0] = array[i][0];
               array[i][0] = temp[0][0];

               temp[0][1] = array[s][1];
               array[s][1] = array[i][1];
               array[i][1] = temp[0][1];

               temp[0][2] = array[s][2];
               array[s][2] = array[i][2];
               array[i][2] = temp[0][2];
               }

               counter1 = 0;
               counter2 = 0;
               counter3 = 0;
               counter4 = 0;


            /*
            for (int w = 0; w < weeklyGames1*(noOfElements%10); w+=weeklyGames1){
            for (int i = w; i < w + weeklyGames1; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < w+weeklyGames1-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 2 || counter2 > 2)
                  repeat = true;
               }
            }
            for (int w = weeklyGames1*(noOfElements%10); w < noOfElements; w+=weeklyGames2){
            for (int i = w; i < w + weeklyGames2; i++){
               counter1 = 0;
               counter2 = 0;
               System.out.println(w);
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < w+weeklyGames2-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 2 || counter2 > 2)
                  repeat = true;
               }
            }*/
            //Week1
            for (int i = weekPart[0]; i < weekPart[1]; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[1]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 1 || counter2 > 1)
                  repeat = true;
               }
            //Week2
            for (int i = weekPart[1]; i < weekPart[2]; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[2]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 1 || counter2 > 1)
                  repeat = true;
               }
            //Week3
            for (int i = weekPart[2]; i < weekPart[3]; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[3]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 1 || counter2 > 1)
                  repeat = true;
               }
            //Week4
            for (int i = weekPart[3]; i < weekPart[4]; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[4]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter2++;
                     }
                  }
               if (counter1 > 1 || counter2 > 1)
                  repeat = true;
               }
            //Week5
            for (int i = weekPart[4]; i < weekPart[5]; i++){
               counter1 = 0;
               counter2 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[5]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                        counter1++;
                     if (array[i][2].equals(array[i+k][j]))
                        counter2++;
                     }
                  }
               if (counter1 > 1 || counter2 > 1)
                  repeat = true;
               }
            //Week6
            for (int i = weekPart[5]; i < weekPart[6]; i++){
               counter3 = 0;
               counter4 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[6]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter3++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter4++;
                     }
                  }
               if (counter3 > 1 || counter4 > 1)
                  repeat = true;
               }
            //Week7
            for (int i = weekPart[6]; i < weekPart[7]; i++){
               counter3 = 0;
               counter4 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[7]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter3++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter4++;
                     }
                  }
               if (counter3 > 1 || counter4 > 1)
                  repeat = true;
               }
            //Week8
            for (int i = weekPart[7]; i < weekPart[8]; i++){
               counter3 = 0;
               counter4 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[8]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter3++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter4++;
                     }
                  }
               if (counter3 > 1 || counter4 > 1)
                  repeat = true;
               }
            //Week9
            for (int i = weekPart[8]; i < weekPart[9]; i++){
               counter3 = 0;
               counter4 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[9]-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter3++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter4++;
                     }
                  }
               if (counter3 > 1 || counter4 > 1)
                  repeat = true;
               }
            //Week10
            for (int i = weekPart[9]; i < weekPart[9]+weeklyGames2; i++){
               counter3 = 0;
               counter4 = 0;
               for (int j = 1; j < 3; j++){
                  for (int k = 1; k < weekPart[9]+weeklyGames2-i; k++){
                     if (array[i][1].equals(array[i+k][j]))
                     counter3++;
                     if (array[i][2].equals(array[i+k][j]))
                     counter4++;
                     }
                  }
               if (counter3 > 1 || counter4 > 1)
                  repeat = true;
               }
            } while (repeat);

            return array;
         }
public static void main (String [] args){
      }
   }