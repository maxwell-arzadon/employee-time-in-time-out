package server_package.ServerGUI;

import server_package.ServerApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AdminMenuGUI extends JFrame {
    public JLabel title;
    public JLabel getEmployeeName;
    public JButton regRequests;
    public JButton checkEmpStatus;
    public JButton generateEmpReport;
    public JButton closeAppButton;
    public JButton updateCredentials;
    public JLabel display;
    public ImageIcon background;

    public AdminMenuGUI(ServerApplication server) {
        try {
            background = new ImageIcon(getClass().getResource("ServerGUIResources/pixel-cityscape.gif"));
            display = new JLabel(background);
            this.setContentPane(display);
        } catch (Exception e) {
            System.out.println("Image cannot be found.");
        }

        regRequests = new JButton("REGISTRATION REQUESTS");
        checkEmpStatus = new JButton("CHECK EMPLOYEE STATUS");
        generateEmpReport = new JButton("GENERATE EMPLOYEE REPORT");
        closeAppButton = new JButton("LOG OUT");
        updateCredentials = new JButton("UPDATE CREDENTIALS");

        title = new JLabel("KRONOS", SwingConstants.CENTER);
        title.setBounds(35, 40, 300,65);
        title.setFont(new Font("Algerian", Font.BOLD,40));
        title.setForeground(new Color(0xF2E9E4));
        display.add(title);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\server_package\\ServerGUI\\ServerGUIResources\\digital.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            title.setFont(customFont.deriveFont(70f));
        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }

        getEmployeeName = new JLabel("SERVER ADMINISTRATOR",SwingConstants.CENTER); //name of client
        getEmployeeName.setBounds(10, 110, 350,25);
        getEmployeeName.setFont(new Font("Calibri", Font.BOLD,25));
        getEmployeeName.setForeground(new Color(0xF2E9E4));
        display.add(getEmployeeName);

        regRequests.setBounds(35, 210, 300, 40);
        regRequests.setFont(new Font("Calibri", Font.BOLD, 18));
        regRequests.setBackground(new Color(0xF2E9E4));
        regRequests.setForeground(new Color(0x4A4E69));
        regRequests.setUI(new StyledButtonUI());
        display.add(regRequests);

        checkEmpStatus.setBounds(35, 280, 300, 40);
        checkEmpStatus.setFont(new Font("Calibri", Font.BOLD, 18));
        checkEmpStatus.setBackground(new Color(0xF2E9E4));
        checkEmpStatus.setForeground(new Color(0x4A4E69));
        checkEmpStatus.setUI(new StyledButtonUI());
        display.add(checkEmpStatus);

        generateEmpReport.setBounds(35, 350, 300, 40);
        generateEmpReport.setFont(new Font("Calibri", Font.BOLD, 18));
        generateEmpReport.setBackground(new Color(0xF2E9E4));
        generateEmpReport.setForeground(new Color(0x4A4E69));
        generateEmpReport.setUI(new StyledButtonUI());
        display.add(generateEmpReport);

        updateCredentials.setBounds(35, 420, 300, 40);
        updateCredentials.setFont(new Font("Calibri", Font.BOLD, 18));
        updateCredentials.setBackground(new Color(0xF2E9E4));
        updateCredentials.setForeground(new Color(0x4A4E69));
        updateCredentials.setUI(new StyledButtonUI());
        display.add(updateCredentials);

        closeAppButton.setBounds(35, 490, 190, 30);
        closeAppButton.setFont(new Font("Calibri", Font.BOLD, 15));
        closeAppButton.setBackground(new Color(0x4A4E69));
        closeAppButton.setForeground(Color.white);
        closeAppButton.setUI(new StyledButtonUI());
        display.add(closeAppButton);

        this.setTitle("KRONOS: Time In & Time Out Application");
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocation(20, 50);

        regRequests.setText("REGISTRATION REQUESTS");
        regRequests.addActionListener(evt -> {
            regRequests.setEnabled(false);
            new RegistrationRequestsGUI(server, regRequests);
            regRequests.setBackground(new Color(0x4A4E69));
        });

        checkEmpStatus.setText("CHECK EMPLOYEE STATUS");
        checkEmpStatus.addActionListener(evt -> {
            try {
                checkEmpStatus.setEnabled(false);
                checkEmpStatus.setBackground(new Color(0x4A4E69));
                new EmpStatusListGUI(server, checkEmpStatus);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        generateEmpReport.setText("GENERATE EMPLOYEE REPORT");
        generateEmpReport.addActionListener(evt -> {
            try {
                generateEmpReport.setEnabled(false);
                generateEmpReport.setBackground(new Color(0x4A4E69));
                new GenerateReportListGUI(server, generateEmpReport);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An IOException has occurred.");
                this.dispose();
            }
        });

        updateCredentials.setText("EDIT ADMIN PROFILE");
        updateCredentials.addActionListener(evt -> {
            new AdminEditCredentialsGUI(server, updateCredentials);
        });


        closeAppButton.setText("CLOSE APPLICATION");
        closeAppButton.addActionListener(evt -> {
            try {
                server.stopServer();
            } catch (NotBoundException | RemoteException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Thank You for using KRONOS!",
                    "Close Window", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        });
    }
}
