package server_package.ServerGUI;

import server_package.ServerApplication;
import server_package.server_classes.AttendanceManager;
import server_package.server_classes.WorkTimeComputation;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class GenerateReportListGUI extends JFrame {
    Calendar start;
    Calendar end;
    String[] months = { "January","February", "March","April","May","June", "July", "August","September", "October", "November", "December"};

    /**
     * Generates a list of dates and hours rendered per date.
     * Initially opens a dialog box to accept user input, specifically Start Date and End Date.
     * @param buttonFromMenu The button from the previous GUI that invoked GenerateReportListGUI
     */
    public GenerateReportListGUI(ServerApplication server, JButton buttonFromMenu) throws IOException {
        this.setSize(330, 200);
        buttonFromMenu.setEnabled(false);
        buttonFromMenu.setBackground(new Color(0x4A4E69));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(330, 200));
        panel.setLayout(null);

        JTextArea instruction = new JTextArea();
        instruction.setText("""
                Provide a Start and End Date to compute the\s
                hours rendered by all employees within the given\s
                time period.""");
        instruction.setBounds(0, 0, 320,60);
        instruction.setBackground(null);
        instruction.setFont(new Font("Corbel", Font.PLAIN, 14));
        instruction.setEditable(false);
        panel.add(instruction);

        //Start Date details
        JComboBox<String> startMonth = new JComboBox<>(months);
        startMonth.setMaximumSize(startMonth.getMinimumSize());
        startMonth.setBounds(0, 70, 100, 25);
        panel.add(startMonth);
        JTextField startDay = new JTextField(SwingConstants.CENTER);
        startDay.setToolTipText("DAY");
        startDay.setBounds(123, 70, 50,25);
        startDay.setBackground(new Color(0xF2E9E4));
        panel.add(startDay);
        JTextField startYear = new JTextField(SwingConstants.CENTER);
        startYear.setToolTipText("YEAR");
        startYear.setBounds(200, 70, 100,25);
        startYear.setBackground(new Color(0xF2E9E4));
        panel.add(startYear);
        JLabel startDate = new JLabel("Start Date", SwingConstants.CENTER);
        startDate.setBounds(0, 90, 300,25);
        startDate.setForeground(new Color(0x22223B));
        panel.add(startDate);

        //End Date details
        JComboBox<String> endMonth = new JComboBox<>(months);
        endMonth.setMaximumSize(startMonth.getMinimumSize());
        endMonth.setBounds(0, 130, 100, 25);
        panel.add(endMonth);
        JTextField endDay = new JTextField(SwingConstants.CENTER);
        endDay.setToolTipText("DAY");
        endDay.setBounds(123, 130, 50,25);
        endDay.setBackground(new Color(0xF2E9E4));
        panel.add(endDay);
        JTextField endYear = new JTextField(SwingConstants.CENTER);
        endYear.setToolTipText("YEAR");
        endYear.setBounds(200, 130, 100,25);
        endYear.setBackground(new Color(0xF2E9E4));
        panel.add(endYear);
        JLabel endDate = new JLabel("End Date", SwingConstants.CENTER);
        endDate.setBounds(0, 150, 300,25);
        endDate.setForeground(new Color(0x22223B));
        panel.add(endDate);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Please Enter The Following Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.CLOSED_OPTION) {
            buttonFromMenu.setEnabled(true);
            buttonFromMenu.setBackground(new Color(0xF2E9E4));
            this.dispose();
        }

        if (result == JOptionPane.OK_OPTION) {
            try {

                if (Integer.parseInt(startDay.getText()) > 31 || Integer.parseInt(startDay.getText()) < 1 || Integer.parseInt(endDay.getText()) > 31 || Integer.parseInt(endDay.getText()) < 1) {
                    JOptionPane.showMessageDialog(null, "Invalid day.");
                    new GenerateReportListGUI(server, buttonFromMenu);
                }

                start = new GregorianCalendar(Integer.parseInt(startYear.getText()), startMonth.getSelectedIndex(), Integer.parseInt(startDay.getText()));
                end = new GregorianCalendar(Integer.parseInt(endYear.getText()), endMonth.getSelectedIndex(), Integer.parseInt(endDay.getText()));

                if (start.after(end)) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Please try again.");
                    new GenerateReportListGUI(server, buttonFromMenu);
                } else {
                    //proceed to display GUI
                    generatedReport(server, buttonFromMenu);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number format! Please try again.");
                e.printStackTrace();
                new GenerateReportListGUI(server, buttonFromMenu);
            }
        }if (result == JOptionPane.OK_CANCEL_OPTION) {
            buttonFromMenu.setEnabled(true);
            buttonFromMenu.setBackground(new Color(0xF2E9E4));
            this.dispose();
        }

    }

    /**
     * GUI showing a list of dates containing employee attendance within the specified date range. Clicking on a date
     * from the list will proceed to GenerateReportDetailsGUI to show details of the date.
     * @param buttonFromMenu button from the previous GUI that invoked generatedReport.
     */
    public void generatedReport(ServerApplication server, JButton buttonFromMenu) throws IOException {
        AttendanceManager attendanceManager = new AttendanceManager();
        buttonFromMenu.setEnabled(false);
        buttonFromMenu.setBackground(new Color(0x4A4E69));

        this.setTitle("KRONOS: List of Hours Rendered Per Day");
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(550, 650));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "TOTAL HOURS RENDERED",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 20), new Color(0xF2E9E4)));

        JLabel dateLabel = new JLabel("Dates with attendance from "+months[start.getTime().getMonth()]+"/"+start.getTime().getDate()+"/"+(start.getTime().getYear()+1900)+" - "+
                months[end.getTime().getMonth()]+"/"+end.getTime().getDate()+"/"+(end.getTime().getYear()+1900)+":", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        dateLabel.setForeground(new Color(0xF2E9E4));
        dateLabel.setBounds(0, 40, 550, 30);
        panel.add(dateLabel);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String startString = String.format("%02d", (start.getTime().getMonth()+1))+"/"+
                String.format("%02d", (start.getTime().getDate()))+"/"+(start.getTime().getYear()+1900);
        String endString = String.format("%02d", (end.getTime().getMonth()+1))+"/"+
                String.format("%02d", (end.getTime().getDate()))+"/"+(end.getTime().getYear()+1900);


        ArrayList<Date> receivedDates = new ArrayList<>();
        try {
            //from GUI
            Date startDate = sdf.parse(startString);
            Date endDate = sdf.parse(endString);

            receivedDates = (ArrayList<Date>) attendanceManager.getAttendanceDate(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error parsing data.");
            new GenerateReportListGUI(server, buttonFromMenu);
            this.dispose();
        }

        String[][] dateList = new String[receivedDates.size()][2];
        int totalHoursOverall = 0;
        int totalMinutesOverall = 0;

        for (int x=0; x<dateList.length; x++) {
            dateList[x][0]=
                    months[receivedDates.get(x).getMonth()]+" "+
                    receivedDates.get(x).getDate()+", "+
                    (receivedDates.get(x).getYear()+1900);

            String date = String.format("%02d", (receivedDates.get(x).getMonth()+1))+"/"+
                    String.format("%02d", (receivedDates.get(x).getDate()))+"/"+
                    (receivedDates.get(x).getYear()+1900);
            ArrayList<String> time = WorkTimeComputation.getTimeRenderedPerDay(date);
            int totalHours = 0;
            int totalMinutes = 0;
            for (String s : time) {
                String[] splitTime = s.split(":");
                totalHours += Integer.parseInt(splitTime[0]);
                totalMinutes += Integer.parseInt(splitTime[1]);
            }
            totalHoursOverall += totalHours;
            totalMinutesOverall += totalMinutes;
            dateList[x][1]=totalHours+"HRS "+totalMinutes+"MINS"; //total Hours for that day
        }

        JLabel totalTime = new JLabel("Overall Time Rendered: "+totalHoursOverall+"HRS "+totalMinutesOverall+"MINS", SwingConstants.CENTER);
        totalTime.setFont(new Font("Verdana", Font.BOLD, 14));
        totalTime.setForeground(new Color(0xF2E9E4));
        totalTime.setBounds(0, 515, 550, 30);
        panel.add(totalTime);


        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(0xF2E9E4));
        tablePanel.setBounds(30, 70, 480, 430);

        String[] nameOfColumns = {"DATE", "TOTAL TIME RENDERED"};
        JTable dateTimeRenderedTable = new JTable(dateList, nameOfColumns) {
            //prevent editing of JTable
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells non-editable
                return false;
            }
        };
        dateTimeRenderedTable.setRowHeight(dateTimeRenderedTable.getRowHeight() + 18);
        dateTimeRenderedTable.setDragEnabled(false);
        dateTimeRenderedTable.setCellSelectionEnabled(false);
        dateTimeRenderedTable.isCellEditable(0, 0);

        dateTimeRenderedTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        dateTimeRenderedTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        dateTimeRenderedTable.setRowSelectionAllowed(true);
        dateTimeRenderedTable.setSelectionMode(0);
        tablePanel.add(new JScrollPane(dateTimeRenderedTable));
        this.add(tablePanel);

        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x55576C));
        panel.setPreferredSize(new Dimension(550, 370));
        panel.setLayout(null);



        JButton detailsButton;
        detailsButton = new JButton("SHOW DETAILS");
        detailsButton.setBounds(280, 565, 130, 30);
        detailsButton.setFont(new Font("Calibri", Font.BOLD, 15));
        detailsButton.setBackground(new Color(0xF2E9E4));
        detailsButton.setForeground(new Color(0x22223B));
        detailsButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        ArrayList<Date> finalReceivedDates = receivedDates;
        detailsButton.addActionListener(e -> {
            if (dateTimeRenderedTable.getSelectedRow() == -1)
                JOptionPane.showMessageDialog(null, "Select a date first.");
            else {
                //proceed
                detailsButton.setEnabled(false);
                detailsButton.setBackground(new Color(0x4A4E69));

                int x = dateTimeRenderedTable.getSelectedRow();

                String formattedMonth = (finalReceivedDates.get(x).getMonth()+1)+"";
                if ((finalReceivedDates.get(x).getMonth()+1) < 10)
                    formattedMonth = "0"+(finalReceivedDates.get(x).getMonth()+1);
                String date = formattedMonth+"/"+
                        finalReceivedDates.get(x).getDate()+"/"+
                        (finalReceivedDates.get(x).getYear()+1900);

                try {
                    new GenerateReportDetailsGUI(detailsButton, date, dateList[dateTimeRenderedTable.getSelectedRow()][1]);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An IOException has occurred!");
                }
            }
        });
        panel.add(detailsButton);

        JButton backButton;
        backButton = new JButton("BACK");
        backButton.setBounds(415, 565, 100, 30);
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
        this.add(panel);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setSize(550, 650);
        this.setLocation(400, 50);
    }
}
