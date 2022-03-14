import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class MiddleTier extends JFrame implements ActionListener {

    private JButton insertBt;
    private JButton countBt;

    private InsertGui insertInfo;
    private CountGui countInfo;

    private Connection con;
    private Statement stmt;

    private ResultSet monthCnf;
    private ResultSet monthJnl;
    private ResultSet monthBk;

    private ResultSet upcomingEvent;

    MiddleTier(){
        setTitle("Middle GUI");
        setSize(500, 100);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c1 = getContentPane();
        c1.setLayout(new FlowLayout());

        insertBt = new JButton("Q1 INSERT DATA");
        insertBt.addActionListener(this);
        c1.add(insertBt);

        countBt = new JButton("Q2 COUNT DATA");
        countBt.addActionListener(this);
        c1.add(countBt);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==insertBt) {
            insertInfo = new InsertGui();
            insertInfo.setMtForInsert(this);
        }else if(e.getSource()==countBt) {
            countInfo = new CountGui();
            ConnectDBForCount();
        }
    }

    private boolean InsertToDB(InsertGui insertInfo){

        String insertEventSql = String.format("INSERT INTO Event VALUES('%s','%s','%s');", insertInfo.getEventName(), insertInfo.getCfpText(), insertInfo.getLink());

        String insertTypeEventSql = "";
        if(insertInfo.gettype().equals("Conference")){
            insertTypeEventSql = String.format("INSERT INTO EventConference VALUES('%s','%s','%s','%s');", insertInfo.getEventName(), insertInfo.getCnfCity(), insertInfo.getCnfCountry(), insertInfo.getCnfEventDate());
        }
        else if(insertInfo.gettype().equals("Journal")) {
            insertTypeEventSql = String.format("INSERT INTO EventJournal VALUES('%s','%s','%s');", insertInfo.getEventName(), insertInfo.getJournalName(), insertInfo.getJournalPublisher());
        }
        else if(insertInfo.gettype().equals("Book")){
            insertTypeEventSql = String.format("INSERT INTO EventBook VALUES('%s','%s');", insertInfo.getEventName(), insertInfo.getBookPublisher());
        }

        ArrayList<String> pplInsert = new ArrayList<>();
        String orgnzInsert = "INSERT INTO Organizes VALUES";
        for(int i = 0; i < insertInfo.getPplList().size(); i++){
            pplInsert.add(String.format("INSERT INTO People VALUES('%s','%s');",insertInfo.getPplList().get(i)[0],insertInfo.getPplList().get(i)[1]));
            if(i == insertInfo.getPplList().size() - 1){
                orgnzInsert += String.format("('%s','%s','%s');",insertInfo.getEventName(),insertInfo.getPplList().get(i)[0],insertInfo.getPplList().get(i)[2]);
            }
            else{
                orgnzInsert += String.format("('%s','%s','%s'),",insertInfo.getEventName(),insertInfo.getPplList().get(i)[0],insertInfo.getPplList().get(i)[2]);
            }
        }

        String atvInsert = "INSERT INTO ActivityHappens VALUES";
        for(int i = 0; i < insertInfo.getAtvList().size(); i++){
            if(i == insertInfo.getAtvList().size() - 1){
                atvInsert += String.format("('%s','%s','%s');",insertInfo.getEventName(),insertInfo.getAtvList().get(i)[0],insertInfo.getAtvList().get(i)[1]);
            }
            else{
                atvInsert += String.format("('%s','%s','%s'),",insertInfo.getEventName(),insertInfo.getAtvList().get(i)[0],insertInfo.getAtvList().get(i)[1]);
            }
        }

        ArrayList<String> tpInsert  = new ArrayList<>();
        String cvInsert = "INSERT INTO Covers VALUES";
        for(int i = 0; i < insertInfo.getTpList().size(); i++){
            tpInsert.add(String.format("INSERT INTO ResearchTopic VALUES('%s','%s');",insertInfo.getTpList().get(i)[0],insertInfo.getTpList().get(i)[1]));
            if(i == insertInfo.getTpList().size() - 1){
                cvInsert += String.format("('%s','%s');",insertInfo.getEventName(),insertInfo.getTpList().get(i)[0]);
            }
            else{
                cvInsert += String.format("('%s','%s'),",insertInfo.getEventName(),insertInfo.getTpList().get(i)[0]);
            }
        }


        try{
            stmt.executeUpdate(insertEventSql);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Query Insertion Fail: " + e.getMessage());
            return false;
        }

        for(String s : pplInsert){
            try{
                stmt.executeUpdate(s);
            } catch(SQLException e){
                //ignore the same name people in DB
                //(I assumed that if the same people name is already in DB, it has the same affiliation and we don't need to add to DB.
                // Just add organizes.)
            }
        }

        for(String s : tpInsert){
            try{
                stmt.executeUpdate(s);
            } catch(SQLException e){
                //ignore the same name topic in DB
                //I assumed that if the same topic name is already in DB, it has the same area and we don't need to add to DB.
                //Just add covers.)
            }
        }

        try{
            stmt.executeUpdate(insertTypeEventSql);

            if(insertInfo.getPplList().size() > 0){
                stmt.executeUpdate(orgnzInsert);
            }

            if(insertInfo.getAtvList().size() > 0){
                stmt.executeUpdate(atvInsert);
            }

            if(insertInfo.getTpList().size() > 0){
                stmt.executeUpdate(cvInsert);
            }
            return true;
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Query Insertion Fail: " + e.getMessage());
            return false;
        }

    }

    public boolean ConnectDBForInsert(){
        try{
            String user = "root";
            String pw = "rla910614";
            //change later to null to submit
            String url = "jdbc:mysql://localhost:3306/3421a03"; // change later
            con = DriverManager.getConnection(url,user,pw);
            stmt = con.createStatement();
            if(InsertToDB(insertInfo)){
                con.close();
                return true;
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Connection Fail: " + e.getMessage());
            return false;
        }
        return false;
    }

    private boolean extractData(){
        try{
            monthCnf = stmt.executeQuery("SELECT MONTH(date) AS Month, COUNT(date) AS Conference_Month_Count FROM eventconference GROUP BY MONTH(date);");
            while(monthCnf.next()){
                countInfo.getMonthCnfMdl().addRow(new String[]{monthCnf.getString("Month"),monthCnf.getString("Conference_Month_Count")});
            }

            monthJnl = stmt.executeQuery("SELECT MONTH(ActivityDate) AS Month_Of_Journal, COUNT(ActivityDate) AS Journal_Count FROM (SELECT Ordered_Table.* FROM \n" +
                    "(SELECT activityhappens.* FROM activityhappens, eventjournal\n" +
                    "WHERE activityhappens.EventName = eventjournal.EventName order by activityhappens.EventName, activityhappens.ActivityDate ASC LIMIT 18446744073709551615) AS Ordered_Table\n" +
                    "group by Ordered_Table.EventName) AS Ordered_J\n" +
                    "GROUP BY MONTH(ActivityDate);");
            while(monthJnl.next()){
                countInfo.getMonthJnlMdl().addRow(new String[]{monthJnl.getString("Month_Of_Journal"),monthJnl.getString("Journal_Count")});
            }

            monthBk = stmt.executeQuery("SELECT MONTH(ActivityDate) AS Month_Of_Book, COUNT(ActivityDate) AS Book_Count FROM (SELECT Ordered_Table.* FROM \n" +
                    "(SELECT activityhappens.* FROM activityhappens, eventbook\n" +
                    "WHERE activityhappens.EventName = eventbook.EventName order by activityhappens.EventName, activityhappens.ActivityDate ASC LIMIT 18446744073709551615) AS Ordered_Table\n" +
                    "group by Ordered_Table.EventName) AS Ordered_B\n" +
                    "GROUP BY MONTH(ActivityDate);\n");
            while(monthBk.next()){
                countInfo.getMonthBkMdl().addRow(new String[]{monthBk.getString("Month_Of_Book"),monthBk.getString("Book_Count")});
            }

            upcomingEvent = stmt.executeQuery("SELECT (SELECT activityhappens.EventName FROM eventconference, activityhappens WHERE activityhappens.EventName = eventconference.EventName ORDER BY activityhappens.ActivityDate ASC LIMIT 1) AS Upcoming_Conference,\n" +
                    "(SELECT activityhappens.EventName FROM eventbook, activityhappens WHERE activityhappens.EventName = eventbook.EventName ORDER BY activityhappens.ActivityDate ASC LIMIT 1) AS Upcoming_Book,\n" +
                    "(SELECT activityhappens.EventName FROM eventjournal, activityhappens WHERE activityhappens.EventName = eventjournal.EventName ORDER BY activityhappens.ActivityDate ASC LIMIT 1) AS Upcoming_Journal;\n");
            while(upcomingEvent.next()){
                countInfo.getUpcTbMdl().addRow(new String[]{upcomingEvent.getString("Upcoming_Conference"),upcomingEvent.getString("Upcoming_Book"),upcomingEvent.getString("Upcoming_Journal")});
            }
            return true;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Connection Fail: " + e.getMessage());
            return false;
        }
    }

    public boolean ConnectDBForCount(){
        try{
            String user = "root";
            String pw = "rla910614";
            //change later to null to submit
            String url = "jdbc:mysql://localhost:3306/3421a03"; // change later
            con = DriverManager.getConnection(url,user,pw);
            stmt = con.createStatement();
            if(extractData()){
                countInfo.setVisible(true);
                return true;
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Connection Fail: " + e.getMessage());
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        new MiddleTier();
    }
}
