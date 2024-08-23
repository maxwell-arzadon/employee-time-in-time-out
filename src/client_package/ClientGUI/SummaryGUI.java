package client_package.ClientGUI;

import client_package.ClientApplication;
import common_package.objects.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class SummaryGUI extends JFrame {
    public JPanel panel;
    public JTable tableOfSummary;
    public JLabel empID;
    public JLabel empName;

    public JLabel currentDate;
    public JLabel time;
    public JPanel upperPanel;
    public JPanel lowerPanel;

    public JButton backButton;
    public JButton closeAppButton;

    public Font customFont;


    public SummaryGUI(ClientApplication client) throws RemoteException {
        User user = client.getUser();
        this.setTitle("KRONOS: Summary of Employee Status Report");
        setResizable(true);
        this.setLocationRelativeTo(null);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\server_package\\ServerGUI\\ServerGUIResources\\digital.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }

        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "EMPLOYEE STATUS REPORT SUMMARY",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font(customFont.getAttributes()), new Color(0xF2E9E4)));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x4A4E69));
        panel.setPreferredSize(new Dimension(550, 570));
        panel.setLayout(null);

        upperPanel = new RoundedPanels();
        upperPanel.setBackground(new Color(0xF2E9E4));
        upperPanel.setBounds(35, 40, 480, 73);
        upperPanel.setLayout(null);

        lowerPanel = new JPanel();
        lowerPanel.setBackground(new Color(0xF2E9E4));
        lowerPanel.setBounds(35, 130, 480, 360);

        empID = new JLabel("Employee ID: " + user.getEmployeeDetails().getEmployeeID());
        empID.setBounds(15, 10, 300, 25);
        empID.setFont(new Font("Verdana", Font.BOLD, 12));
        empID.setForeground(Color.black);
        upperPanel.add(empID);

        empName = new JLabel("Employee Name: " + user.getEmployeeDetails().getEmployeeName());
        empName.setBounds(15, 30, 700, 25);
        empName.setFont(new Font("Verdana", Font.BOLD, 12));
        empName.setForeground(Color.black);
        upperPanel.add(empName);

        LocalDate localDate = LocalDate.now();
        currentDate = new JLabel("Date:  " + localDate);
        currentDate.setBounds(315, 10, 700, 25);
        currentDate.setFont(new Font("Verdana", Font.BOLD, 12));
        currentDate.setForeground(Color.black);
        upperPanel.add(currentDate); 

        //real-time clock
        time = new JLabel();
        time.setText("Time: " + DateFormat.getTimeInstance().format(new Date()));
        Timer timer = new Timer(500, e -> time.setText("Time: " + DateFormat.getTimeInstance().format(new Date())));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        time.setBounds(315, 30, 700, 25);
        time.setFont(new Font("Verdana", Font.BOLD, 12));
        time.setForeground(Color.black);
        upperPanel.add(time);

        List<List<String>>list = client.getSummary();
        String[][] dataArray = new String[list.size()][2];

        for(int i=0; i<list.size();i++){
            for(int j=0;j<list.get(i).size();j++){
                dataArray[i][j] = list.get(i).get(j);
            }
        }

        String[] nameOfColumns = {"STATUS", "TIME"};
        tableOfSummary = new JTable(dataArray, nameOfColumns){
            //prevent editing of JTable
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells non-editable
                return false;
            }
        };
        tableOfSummary.setRowHeight(tableOfSummary.getRowHeight() + 18);
        tableOfSummary.setDragEnabled(false);
        tableOfSummary.setCellSelectionEnabled(false);
        tableOfSummary.isCellEditable(0, 0);

        tableOfSummary.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableOfSummary.getColumnModel().getColumn(1).setPreferredWidth(20);
        lowerPanel.add(new JScrollPane(tableOfSummary));

        backButton = new JButton();
        backButton.setBounds(210, 510, 100, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 15));
        backButton.setBackground(new Color(0x9A8C98));
        backButton.setForeground(Color.white);
        backButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        panel.add(backButton);

        closeAppButton = new JButton();
        closeAppButton.setBounds(320, 510, 190, 30);
        closeAppButton.setFont(new Font("Calibri", Font.BOLD, 15));
        closeAppButton.setBackground(new Color(0x9A8C98));
        closeAppButton.setForeground(Color.white);
        closeAppButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        panel.add(closeAppButton);

        this.add(lowerPanel);
        this.add(upperPanel);
        this.add(panel);

        this.setSize(550, 570);
        this.pack();
        this.setVisible(true);

       backButton.setText("BACK");
        backButton.addActionListener(evt -> {
            dispose();
        });

        closeAppButton.setText("CLOSE APPLICATION");
        closeAppButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(null, "Thank You for using KRONOS!",
                    "Close Window", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        });
    }
}
