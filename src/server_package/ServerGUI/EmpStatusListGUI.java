package server_package.ServerGUI;

import server_package.ServerApplication;
import server_package.server_classes.AttendanceManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class EmpStatusListGUI extends JFrame{
    public JPanel panel;
    public JTable empStatusTable;

    public JLabel currentDate;
    public JLabel time;
    public int hours, minutes, seconds;

    public JPanel lowerPanel;

    public JButton backButton;

    public EmpStatusListGUI(ServerApplication server, JButton buttonFromMenu) throws IOException {
        this.setTitle("KRONOS: Employees' Status List");
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "EMPLOYEES' STATUS LIST",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 20), new Color(0xF2E9E4)));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x4A4E69));
        panel.setPreferredSize(new Dimension(550, 570));
        panel.setLayout(null);

        lowerPanel = new JPanel();
        lowerPanel.setBackground(new Color(0xF2E9E4));
        lowerPanel.setBounds(35, 40, 480, 455);
        currentDate = new JLabel("Date:  " + LocalDate.now());
        currentDate.setBounds(315, 10, 700, 25);
        currentDate.setFont(new Font("Verdana", Font.BOLD, 12));
        currentDate.setForeground(Color.black);
        lowerPanel.add(currentDate);




        AttendanceManager attendanceManager = new AttendanceManager();
        List<List<String>>list = attendanceManager.generateEmployeeStatusList(); // reads the latest date on the json file
        String[][] dataArray = new String[list.size()][2];

        for(int i=0; i<list.size();i++){
            for(int j=0;j<list.get(i).size();j++){
                dataArray[i][j] = list.get(i).get(j);
                System.out.println(dataArray[i][j]);
            }
        }

        String[] nameOfColumns = {"EMPLOYEE", "STATUS"};
        empStatusTable = new JTable(dataArray, nameOfColumns) {
            //prevent editing of JTable
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells non-editable
                return false;
            }
        };
        empStatusTable.setRowHeight(empStatusTable.getRowHeight() + 18);
        empStatusTable.setDragEnabled(false);
        empStatusTable.setCellSelectionEnabled(false);
        empStatusTable.isCellEditable(0, 0);

        empStatusTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        empStatusTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        lowerPanel.add(new JScrollPane(empStatusTable));

        backButton = new JButton("BACK");
        backButton.setBounds(415, 510, 100, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 15));
        backButton.setBackground(new Color(0x9A8C98));
        backButton.setForeground(Color.white);
        backButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        backButton.addActionListener(e -> {
            buttonFromMenu.setEnabled(true);
            buttonFromMenu.setBackground(new Color(0xF2E9E4));
            this.dispose();
        });
        panel.add(backButton);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(lowerPanel);
        this.add(panel);
        this.setResizable(false);
        this.setSize(550, 570);
        this.pack();
        this.setVisible(true);
        this.setLocation(400, 50);
    }
}
