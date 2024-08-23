package server_package.ServerGUI;

import server_package.server_classes.WorkTimeComputation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateReportDetailsGUI extends JFrame {
    public JButton backButton;

    /**
     * Based on a chosen date from GenerateReportListGUI, this GUI will show the list of employees present during that date
     * as well as their total time rendered.
     * @param buttonFromPrevious button from the previous GUI that invoked GenerateReportDetailsGUI
     * @param date chosen date from GenerateReportDetailsGUI
     * @param hoursRendered total hours rendered from the chosen date from GenerateReportDetailsGUI
     */
    public GenerateReportDetailsGUI(JButton buttonFromPrevious, String date, String hoursRendered) throws IOException {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(550, 600));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), date,
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 20), new Color(0xF2E9E4)));

        JLabel dateLabel = new JLabel("Total Time Rendered For The Day: "+hoursRendered, SwingConstants.CENTER);
        dateLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        dateLabel.setForeground(new Color(0xF2E9E4));
        dateLabel.setBounds(0, 40, 550, 30);
        panel.add(dateLabel);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(0xF2E9E4));
        tablePanel.setBounds(30, 70, 480, 430);
        ArrayList<String> empList = WorkTimeComputation.getEmployeeAttendancePerDay(date);
        ArrayList<String> timeList = WorkTimeComputation.getTimeRenderedPerDay(date);


        String[][] data = new String[empList.size()][2];
        for (int x=0; x<empList.size(); x++) {
            data[x][0] = empList.get(x);
            String time = timeList.get(x);
            String[] timeSplit = time.split(":");
            String hours = String.format("%02d", Integer.parseInt(timeSplit[0]));
            String minutes = String.format("%02d", Integer.parseInt(timeSplit[1]));
            data[x][1] = hours+":"+minutes;
        }


        String[] nameOfColumns = {"EMPLOYEE NAME", "TOTAL HOURS WORKED"};
        JTable empStatusTable = new JTable(data, nameOfColumns) {
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
        tablePanel.add(new JScrollPane(empStatusTable));
        this.add(tablePanel);

        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x8E8D96));
        panel.setPreferredSize(new Dimension(550, 370));
        panel.setLayout(null);

        backButton = new JButton("BACK");
        backButton.setBounds(415, 510, 100, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 15));
        backButton.setBackground(new Color(0x9A8C98));
        backButton.setForeground(Color.white);
        backButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        backButton.addActionListener(e -> {
            buttonFromPrevious.setEnabled(true);
            buttonFromPrevious.setBackground(new Color(0xF2E9E4));
            this.dispose();
        });
        panel.add(backButton);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(panel);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setSize(550, 600);
        this.setLocation(940, 50);
    }
}
