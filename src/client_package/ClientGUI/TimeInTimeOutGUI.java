package client_package.ClientGUI;

import client_package.ClientApplication;
import common_package.objects.User;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeInTimeOutGUI extends JFrame { // implements Runnable
    public JLabel title, getEmployeeName, curTime, curDate;
    public JButton timeInButton;
    public JButton timeOutButton;
    public JButton empReportSummaryButton;
    public JButton profileDetailsButton;
    public JButton closeAppButton;
    public JLabel display;
    public ImageIcon background;
    public Font customFont;

    public TimeInTimeOutGUI(ClientApplication client) throws RemoteException {
        User user = client.getUser();
        try {
            background = new ImageIcon(getClass().getResource("ClientGUIResources/background.png"));
            display = new JLabel(background);
        } catch (Exception e) {
            System.out.println("Image cannot be found.");
        }

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\server_package\\ServerGUI\\ServerGUIResources\\digital.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }

        timeInButton = new JButton("TIME IN");
        timeOutButton = new JButton("TIME OUT");
        empReportSummaryButton = new JButton("EMPLOYEE REPORT SUMMARY");
        closeAppButton = new JButton("LOG OUT");

        title = new JLabel("K  R  O  N  O  S");
        title.setBounds(440, 30, 275, 55);
        title.setFont(new Font("Algerian", Font.BOLD, 40));
        title.setForeground(new Color(0xfeea86));
        display.add(title);

        getEmployeeName = new JLabel("Welcome " + user.getEmployeeDetails().getEmployeeName() + "!!", SwingConstants.CENTER); //name of client
        getEmployeeName.setBounds(400, 120, 300, 25);
        getEmployeeName.setFont(customFont.deriveFont(20f));
        getEmployeeName.setForeground(new Color(0xC9ADA7));
        display.add(getEmployeeName);

        timeInButton.setBounds(380, 200, 150, 80);
        timeInButton.setFont(new Font("Calibri", Font.BOLD, 25));
        timeInButton.setBackground(new Color(0xF2E9E4));
        timeInButton.setForeground(new Color(0x4A4E69));
        timeInButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        timeInButton.setEnabled(client.getStatus().equals("break"));
        display.add(timeInButton);

        timeOutButton.setBounds(560, 200, 150, 80);
        timeOutButton.setFont(new Font("Calibri", Font.BOLD, 25));
        timeOutButton.setBackground(new Color(0x595151));
        timeOutButton.setForeground(new Color(0x4A4E69));
        timeOutButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        timeOutButton.setEnabled(client.getStatus().equals("working"));
//        timeOutButton.setEnabled(false);
        display.add(timeOutButton);

        empReportSummaryButton.setBounds(400, 320, 300, 40);
        empReportSummaryButton.setFont(new Font("Calibri", Font.BOLD, 18));
        empReportSummaryButton.setBackground(new Color(0xF2E9E4));
        empReportSummaryButton.setForeground(new Color(0x4A4E69));
        empReportSummaryButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        display.add(empReportSummaryButton);

        profileDetailsButton = new JButton();
        profileDetailsButton.setBounds(480, 390, 140, 30);
        profileDetailsButton.setFont(new Font("Calibri", Font.BOLD, 15));
        profileDetailsButton.setBackground(new Color(0x9A8C98));
        profileDetailsButton.setForeground(Color.white);
        profileDetailsButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        display.add(profileDetailsButton);

        closeAppButton.setBounds(560, 450, 190, 30);
        closeAppButton.setFont(new Font("Calibri", Font.BOLD, 15));
        closeAppButton.setBackground(new Color(0x4A4E69));
        closeAppButton.setForeground(Color.white);
        closeAppButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        display.add(closeAppButton);

        curTime = new JLabel();
        curTime.setBounds(630, 450, 1000, 30);
        curTime.setBackground(new Color(0x4A4E69));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
//        ZoneId zoneId = ZoneId.of("Japan"); // to be used when the application as a whole is working
        ZoneId zoneId = ZoneId.of(client.getTimeZone());
        curTime.setText("Time: " + ZonedDateTime.ofInstant(Instant.now(),zoneId).format(formatter));
        Timer timer = new Timer(500, e -> curTime.setText(" Time: " + ZonedDateTime.ofInstant(Instant.now(),zoneId)
                .format(formatter)));
        Timer timer2 = new Timer(500, e -> curTime.setForeground(new Color(0xF2E9E4)));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer2.setRepeats(true);
        timer2.setCoalesce(true);
        timer2.setInitialDelay(0);
        timer.start();
        timer2.start();

        LocalDate localDate = LocalDate.now(zoneId);
        curDate = new JLabel(" Date:  " + localDate);
        curDate.setBounds(50, 70, 200, 45);
        curDate.setForeground(new Color(0xF2E9E4));
        curDate.setFont(new Font("Verdana", Font.BOLD, 12));
        curDate.setOpaque(true);
        curDate.setBackground(new Color(0x4A4E69));
        display.add(curDate);

        curTime.setBounds(50, 30, 200, 45);
        curTime.setForeground(new Color(0x22223B));
        curTime.setFont(new Font("Verdana", Font.BOLD, 18));
        curTime.setOpaque(true);
        display.add(curTime);

        this.setTitle("KRONOS: Time In & Time Out Application");
        this.add(display);
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeInButton.setText("TIME IN");
        timeInButton.addActionListener(evt -> {
            timeInButton.setBackground(new Color(0x595151));
            timeOutButton.setBackground(new Color(0xF2E9E4));
            timeInButton.setEnabled(false);
            timeOutButton.setEnabled(true);
            try {
                LocalDateTime localDateTime = client.timeIn(); // retrieve  date and time from time in
                String date = dateFormatter.format(localDateTime);
                String time = timeFormatter.format(localDateTime);

                JOptionPane.showMessageDialog(null, "You have timed in at "+ time +" " +
                        "on " + date +".", "Information Window", JOptionPane.INFORMATION_MESSAGE);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        if (!timeInButton.isEnabled()) {
            timeInButton.setBackground(new Color(0x595151));
            timeOutButton.setBackground(new Color(0xF2E9E4));
        }
        timeOutButton.setText("TIME OUT");
        timeOutButton.addActionListener(evt -> {
            timeOutButton.setBackground(new Color(0x595151));
            timeInButton.setBackground(new Color(0xF2E9E4));
            timeOutButton.setEnabled(false);
            timeInButton.setEnabled(true);
            try {
                LocalDateTime localDateTime = client.timeOut(); // retrieve date and time for time out
                String date = dateFormatter.format(localDateTime);
                String time = timeFormatter.format(localDateTime);
                JOptionPane.showMessageDialog(null, "You have timed out at "+ time +" " +
                        "on " + date +".", "Information Window", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        if (!timeOutButton.isEnabled()) {
            timeOutButton.setBackground(new Color(0x595151));
            timeInButton.setBackground(new Color(0xF2E9E4));
        }
        empReportSummaryButton.setText("EMPLOYEE REPORT SUMMARY");
        empReportSummaryButton.addActionListener(evt -> {
            try {
                new SummaryGUI(client);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        profileDetailsButton.setText("PROFILE DETAILS");
        profileDetailsButton.addActionListener(e -> {
            new ProfileDetailsGUIC(client);
        });

        closeAppButton.setText("CLOSE APPLICATION");
        closeAppButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(null, "Thank You for using KRONOS!",
                    "Close Window", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        });
    }
}

