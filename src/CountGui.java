import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CountGui extends JFrame {
    private int w = 700;
    private int h = 500;

    private JTable monthCnfTb;
    private JTable monthJnlTb;
    private JTable monthBkTb;

    private JTable upcEventsTb;

    private DefaultTableModel monthCnfMdl;
    private DefaultTableModel monthJnlMdl;
    private DefaultTableModel monthBkMdl;

    private DefaultTableModel upcTbMdl;

    public CountGui() {
        setTitle("CFP Insert GUI");
        setSize(w, h);
        setResizable(false); // not flexible
        setLocationRelativeTo(null); //set at middle
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c1 = getContentPane();
        c1.setLayout(new FlowLayout());

        Font titleFt = new Font("Arial", Font.BOLD, 50);
        Font ft = new Font("Arial", Font.BOLD, 20);

        JLabel title = new JLabel("CFP Count Information");
        title.setFont(titleFt);
        add(title);


        JPanel countInfo1Pan = new JPanel();
        JLabel countInfo1 = new JLabel("Count of each event type grouped based on the calendar months");
        countInfo1.setFont(ft);
        countInfo1Pan.add(countInfo1);
        add(countInfo1Pan);


        JPanel cnfMonthPan = new JPanel();
        String monthCnfTbClm[] = {"Month of Conference", "Total"};

        monthCnfMdl = new DefaultTableModel(monthCnfTbClm, 0);
        monthCnfTb = new JTable(monthCnfMdl);
        cnfMonthPan.add(monthCnfTb);

        JScrollPane sp = new JScrollPane(monthCnfTb);
        sp.setPreferredSize(new Dimension(180, 200));
        cnfMonthPan.add(sp);

        add(cnfMonthPan);


        JPanel jnlMonthPan = new JPanel();
        String monthJnlTbClm[] = {"Month of Journal", "Total"};

        monthJnlMdl = new DefaultTableModel(monthJnlTbClm, 0);
        monthJnlTb = new JTable(monthJnlMdl);
        jnlMonthPan.add(monthJnlTb);

        JScrollPane sp2 = new JScrollPane(monthJnlTb);
        sp2.setPreferredSize(new Dimension(180, 200));
        jnlMonthPan.add(sp2);

        add(jnlMonthPan);

        JPanel bkMonthPan = new JPanel();
        String monthBkTbClm[] = {"Month of Book", "Total"};

        monthBkMdl = new DefaultTableModel(monthBkTbClm, 0);
        monthBkTb = new JTable(monthBkMdl);
        bkMonthPan.add(monthBkTb);

        JScrollPane sp3 = new JScrollPane(monthBkTb);
        sp3.setPreferredSize(new Dimension(180, 200));
        bkMonthPan.add(sp3);

        add(bkMonthPan);

        JPanel countInfo2Pan = new JPanel();
        JLabel countInfo2 = new JLabel("Upcoming event for each event type");
        countInfo2.setFont(ft);
        countInfo2Pan.add(countInfo2);
        add(countInfo2Pan);



        JPanel upTbPan= new JPanel();
        String upcTbClm[] = {"Upcoming conference", "Upcoming Book", "Upcoming Journal"};

        upcTbMdl = new DefaultTableModel(upcTbClm, 0);
        upcEventsTb = new JTable(upcTbMdl);
        upTbPan.add(upcEventsTb);
        JScrollPane sp4 = new JScrollPane(upcEventsTb);
        sp4.setPreferredSize(new Dimension(360, 39));
        upTbPan.add(sp4);

        add(upTbPan);
    }

    public DefaultTableModel getMonthCnfMdl(){
        return monthCnfMdl;
    }
    public DefaultTableModel getMonthJnlMdl(){
        return monthJnlMdl;
    }
    public DefaultTableModel getMonthBkMdl(){
        return monthBkMdl;
    }
    public DefaultTableModel getUpcTbMdl(){
        return upcTbMdl;
    }
}
