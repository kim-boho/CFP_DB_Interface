import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InsertGui extends JFrame{

    private int w = 700;
    private int h = 850;

    private MiddleTier mtForInsert;

    private JCheckBox cnf;
    private JCheckBox jnl;
    private JCheckBox bk;

    private String eventName;
    private String type;
    private String cfpText;
    private String link;

    private String cnfEventDate;
    private String cnfCity;
    private String cnfCountry;

    private String journalName;
    private String journalPublisher;

    private String bookPublisher;

    private JTable pplTable;
    private JTable atvTable;
    private JTable tpTable;

    private JButton finalInsertBt;

    private ArrayList<String[]> pplList = new ArrayList<>();
    private ArrayList<String[]> atvList = new ArrayList<>();
    private ArrayList<String[]> tpList = new ArrayList<>();

    InsertGui() {

        setTitle("CFP Insert GUI");
        setSize(w, h);
        setResizable(false); // not flexible
        setLocationRelativeTo(null); //set at middle
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c1 = getContentPane();
        c1.setLayout(new FlowLayout());

        Font titleFt = new Font("Arial", Font.BOLD, 50);
        Font ft = new Font("Arial", Font.BOLD, 20);
        Font ft2 = new Font("Arial", Font.PLAIN, 15);

        JLabel title = new JLabel("CFP Information to insert");
        title.setFont(titleFt);
        add(title);

        JLabel name = new JLabel("Event Name:");
        name.setFont(ft);
        JTextField nameInput = new JTextField(49);
        add(name); add(nameInput);

        JLabel text = new JLabel("CPF Text:");
        text.setFont(ft);
        JTextField textInput = new JTextField(52);
        add(text); add(textInput);

        JLabel web = new JLabel("Web Link:");
        web.setFont(ft);
        JTextField webInput = new JTextField(52);
        add(web); add(webInput);


        JPanel cnfInfo = new JPanel();

        cnf = new JCheckBox("Conference");
        cnf.setFont(ft2);
        cnfInfo.add(cnf,"West");

        JLabel date = new JLabel("Date(YYYY-MM-DD):");
        date.setFont(ft);
        JTextField dateInput = new JTextField(6);
        dateInput.setEnabled(false);
        cnfInfo.add(date); cnfInfo.add(dateInput);

        JLabel city = new JLabel("City:");
        city.setFont(ft);
        JTextField cityInput = new JTextField(6);
        cityInput.setEnabled(false);
        cnfInfo.add(city); cnfInfo.add(cityInput);

        JLabel country = new JLabel("Country:");
        country.setFont(ft);
        JTextField countryInput = new JTextField(6);
        countryInput.setEnabled(false);
        cnfInfo.add(country); cnfInfo.add(countryInput);

        add(cnfInfo);


        JPanel jnlInfo = new JPanel();

        jnl = new JCheckBox("Journal");
        jnl.setFont(ft2);
        jnlInfo.add(jnl,"West");

        JLabel jnlName = new JLabel("Journal Name:");
        jnlName.setFont(ft);
        JTextField jnlInput = new JTextField(8);
        jnlInput.setEnabled(false);
        jnlInfo.add(jnlName); jnlInfo.add(jnlInput);

        JLabel pbl = new JLabel("Publisher:");
        pbl.setFont(ft);
        JTextField pblInput = new JTextField(8);
        pblInput.setEnabled(false);
        jnlInfo.add(pbl); jnlInfo.add(pblInput);

        add(jnlInfo);


        JPanel bkInfo = new JPanel();

        bk = new JCheckBox("Book");
        bk.setFont(ft2);
        bkInfo.add(bk,"West");

        JLabel bkPbl = new JLabel("Publisher:");
        bkPbl.setFont(ft);
        JTextField bkPblInput = new JTextField(8);
        bkPblInput.setEnabled(false);
        bkInfo.add(bkPbl); bkInfo.add(bkPblInput);

        add(bkInfo);

        cnf.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    jnl.setSelected(false);
                    bk.setSelected(false);

                    dateInput.setEnabled(true);
                    cityInput.setEnabled(true);
                    countryInput.setEnabled(true);
                    jnlInput.setEnabled(false);
                    pblInput.setEnabled(false);
                    bkPblInput.setEnabled(false);
                }
            }
        });

        jnl.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    cnf.setSelected(false);
                    bk.setSelected(false);

                    dateInput.setEnabled(false);
                    cityInput.setEnabled(false);
                    countryInput.setEnabled(false);
                    jnlInput.setEnabled(true);
                    pblInput.setEnabled(true);
                    bkPblInput.setEnabled(false);
                }
            }
        });

        bk.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    jnl.setSelected(false);
                    cnf.setSelected(false);
                    dateInput.setEnabled(false);
                    cityInput.setEnabled(false);
                    countryInput.setEnabled(false);
                    jnlInput.setEnabled(false);
                    pblInput.setEnabled(false);
                    bkPblInput.setEnabled(true);
                }
            }
        });

        JPanel pplPan = new JPanel();

        JLabel pplInfo = new JLabel("People");
        pplInfo.setFont(ft2);
        pplPan.add(pplInfo);

        JLabel pplName = new JLabel("Name:");
        pplName.setFont(ft);
        JTextField pplNameInput = new JTextField(8);
        pplPan.add(pplName); pplPan.add(pplNameInput);

        JLabel afl = new JLabel("Affiliation:");
        afl.setFont(ft);
        JTextField aflInput = new JTextField(8);
        pplPan.add(afl); pplPan.add(aflInput);

        JLabel role = new JLabel("Role:");
        role.setFont(ft);
        JTextField roleInput = new JTextField(8);
        pplPan.add(role); pplPan.add(roleInput);

        JButton insertPpl = new JButton("ADD");

        pplPan.add(insertPpl);

        add(pplPan);


        JPanel atvPan = new JPanel();

        JLabel atvInfo = new JLabel("Activities");
        atvInfo.setFont(ft2);
        atvPan.add(atvInfo);

        JLabel atvName = new JLabel("Activity Name:");
        atvName.setFont(ft);
        JTextField atvNameInput = new JTextField(8);
        atvPan.add(atvName); atvPan.add(atvNameInput);

        JLabel atvDate = new JLabel("Date(YYYY-MM-DD):");
        atvDate.setFont(ft);
        JTextField atvDateInput = new JTextField(8);
        atvPan.add(atvDate); atvPan.add(atvDateInput);

        JButton insertAtv = new JButton("ADD");
        atvPan.add(insertAtv);

        add(atvPan);


        JPanel tpPan = new JPanel();

        JLabel tpInfo = new JLabel("Topics");
        tpInfo.setFont(ft2);
        tpPan.add(tpInfo);

        JLabel tpName = new JLabel("Topic:");
        tpName.setFont(ft);
        JTextField tpNameInput = new JTextField(8);
        tpPan.add(tpName); tpPan.add(tpNameInput);

        JLabel tpArea = new JLabel("Area:");
        tpArea.setFont(ft);
        JTextField tpAreaInput = new JTextField(8);
        tpPan.add(tpArea); tpPan.add(tpAreaInput);
        JButton insertTp = new JButton("ADD");
        tpPan.add(insertTp);

        add(tpPan);


        JPanel pplTablePan = new JPanel();
        pplTablePan.setSize(270, 300);
        String pplTableClm[] = {"Person Name", "Affiliation", "Role"};

        DefaultTableModel ppltbMdl = new DefaultTableModel(pplTableClm, 0);
        pplTable = new JTable(ppltbMdl);
        pplTablePan.add(pplTable);

        JScrollPane sp = new JScrollPane(pplTable);
        sp.setPreferredSize(new Dimension(270, 300));
        pplTablePan.add(sp);

        add(pplTablePan);




        JPanel atvTablePan = new JPanel();
        String atvTableClm[] = {"Activity Name", "Date"};

        DefaultTableModel atvTableMdl = new DefaultTableModel(atvTableClm, 0);
        atvTable = new JTable(atvTableMdl);
        atvTablePan.add(atvTable);

        JScrollPane sp2 = new JScrollPane(atvTable);
        sp2.setPreferredSize(new Dimension(180, 300));
        atvTablePan.add(sp2);

        add(atvTablePan);




        JPanel tpTablePan = new JPanel();
        String tpTableClm[] = {"Topic Name", "Area"};

        DefaultTableModel tpTableMdl = new DefaultTableModel(tpTableClm, 0);
        tpTable = new JTable(tpTableMdl);
        tpTablePan.add(tpTable);

        JScrollPane sp3 = new JScrollPane(tpTable);
        sp3.setPreferredSize(new Dimension(180, 300));
        tpTablePan.add(sp3);

        add(tpTablePan);

        insertPpl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pplNameInput.getText().equals("") || aflInput.getText().equals("") || roleInput.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Error: No information to insert.\nTry Again.");
                    return;
                }
                else{
                    for(String[] s : pplList){
                        if(s[0].equals(pplNameInput.getText())){
                            JOptionPane.showMessageDialog(null, "Error: People name should be distinct.\nTry Again.");
                            return;
                        }
                    }
                    String[] addedRow = new String[]{pplNameInput.getText(), aflInput.getText(), roleInput.getText()};
                    ppltbMdl.addRow(addedRow);
                    pplList.add(addedRow);
                    pplNameInput.setText("");
                    aflInput.setText("");
                    roleInput.setText("");
                }
            }
        });

        insertAtv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(atvNameInput.getText().equals("") || atvDateInput.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Error: No information to insert.\nTry Again.");
                    return;
                }
                else if(!validDate(atvDateInput.getText())){
                    JOptionPane.showMessageDialog(null, "Error: Invalid Date.\nTry Again.");
                    return;
                }
                else{
                    for(String[] s : atvList){
                        if(s[0].equals(atvNameInput.getText())){
                            JOptionPane.showMessageDialog(null, "Error: Activity name should be distinct.\nTry Again.");
                            return;
                        }
                    }
                    String[] addedRow = new String[]{atvNameInput.getText(), atvDateInput.getText()};
                    atvTableMdl.addRow(addedRow);
                    atvList.add(addedRow);
                    atvNameInput.setText("");
                    atvDateInput.setText("");
                }
            }
        });

        insertTp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tpNameInput.getText().equals("") || tpAreaInput.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Error: No information to insert.\nTry Again.");
                    return;
                }
                else{
                    for(String[] s : tpList){
                        if(s[0].equals(tpNameInput.getText())){
                            JOptionPane.showMessageDialog(null, "Error: Topic name should be distinct.\nTry Again.");
                            return;
                        }
                    }
                    String[] addedRow = new String[]{tpNameInput.getText(), tpAreaInput.getText()};
                    tpTableMdl.addRow(addedRow);
                    tpList.add(addedRow);
                    tpNameInput.setText("");
                    tpAreaInput.setText("");
                }
            }
        });


        JPanel removePan = new JPanel();
        removePan.setPreferredSize(new Dimension(w, 30));
        JButton removeBt = new JButton("REMOVE");
        removeBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pplSlc = pplTable.getSelectedRow();
                int atvSlc = atvTable.getSelectedRow();
                int tpSlc = tpTable.getSelectedRow();
                if(pplSlc != -1){
                    ppltbMdl.removeRow(pplSlc);
                    pplList.remove(pplSlc);
                }

                if(atvSlc != -1){
                    atvTableMdl.removeRow(atvSlc);
                    atvList.remove(atvSlc);
                }

                if(tpSlc != -1){
                    tpTableMdl.removeRow(tpSlc);
                    tpList.remove(tpSlc);
                }
            }
        });
        removePan.add(removeBt, "South");
        add(removePan);


        JPanel insertPan = new JPanel();
        finalInsertBt = new JButton("INSERT");
        finalInsertBt.setFont(ft);
        finalInsertBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: Event Name must not be empty.\nTry Again.");
                    return;
                } else {
                    eventName = nameInput.getText();
                }

                cfpText = textInput.getText();
                link = webInput.getText();

                if (!cnf.isSelected() && !jnl.isSelected() && !bk.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Error: Event Type should be selected.\nTry Again.");
                    return;
                }
                else if (cnf.isSelected()) {
                    type = "Conference";
                    if (!validDate(dateInput.getText())) {
                        JOptionPane.showMessageDialog(null, "Error: Invalid Date.\nTry Again.");
                        return;
                    } else {
                        cnfEventDate = dateInput.getText();
                    }
                    cnfCity = cityInput.getText();
                    cnfCountry = countryInput.getText();
                }
                else if (jnl.isSelected()) {
                    type = "Journal";
                    journalName = jnlName.getText();
                    journalPublisher = pblInput.getText();
                }
                else if (bk.isSelected()) {
                    type = "Book";
                    bookPublisher = bkPblInput.getText();
                }
                if(mtForInsert.ConnectDBForInsert()){
                    JOptionPane.showMessageDialog(null,"Data's been inserted");
                    setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Try again");
                }
            }
        });
        insertPan.add(finalInsertBt);
        add(insertPan);


        setVisible(true);
    }

    private static boolean validDate(String dateString){
        String YYYYMMDD = "(19|20)\\d{2}[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";
        if(dateString.matches(YYYYMMDD)){
            return true;
        }
        else{
            return false;
        }
    }
    public void setMtForInsert(MiddleTier mt){
        mtForInsert = mt;
    }


    public String getEventName(){
        return eventName;
    }

    public String getCfpText(){
        return cfpText;
    }

    public String getLink(){
        return link;
    }

    public String gettype(){
        return type;
    }

    public String getCnfEventDate(){
        return cnfEventDate;
    }

    public String getCnfCity(){
        return cnfCity;
    }

    public String getCnfCountry(){
        return cnfCountry;
    }

    public String getJournalName(){
        return journalName;
    }

    public String getJournalPublisher(){
        return journalPublisher;
    }

    public String getBookPublisher(){
        return bookPublisher;
    }

    public ArrayList<String[]> getPplList(){
        return pplList;
    }

    public ArrayList<String[]> getAtvList(){
        return atvList;
    }

    public ArrayList<String[]> getTpList(){
        return tpList;
    }
}
