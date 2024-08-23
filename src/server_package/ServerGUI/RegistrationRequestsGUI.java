package server_package.ServerGUI;

import client_package.ClientGUI.RoundedPanels;
import common_package.Exceptions.InvalidCredentialsException;
import common_package.objects.User;
import server_package.ServerApplication;
import server_package.server_classes.EmployeeDetailsManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRequestsGUI extends JFrame {
    public JPanel panel;
    public JPanel leftPanel;
    public JButton profileDetailsButton;
    public JButton backButton;
    public JButton closeAppButton;
    public JButton acceptButton;
    public JButton rejectButton;
    public JList jList;
    
    public static List<User> registrationList = new ArrayList<>();

    //ACTIVATE THIS GUI FROM THE ADMIN MENU!
    /*
    public static void main(String[] args) {
        new RegistrationRequestsGUI(new Server());
    }*/

    public RegistrationRequestsGUI(ServerApplication server, JButton buttonFromMenu){
        registrationList = new ArrayList<>();
        registrationList = EmployeeDetailsManager.populateRegistrationList();
        String[] regList = new String[registrationList.size()];
        for(int i = 0; i < registrationList.size(); i++){
            regList[i] = registrationList.get(i).getUsername();
        }

        this.setTitle("KRONOS: Registration Requests");
        setResizable(true);
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "REGISTRATION REQUESTS",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 20), new Color(0xF2E9E4)));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x4A4E69));
        panel.setPreferredSize(new Dimension(550, 570));
        panel.setLayout(null);

        leftPanel = new RoundedPanels();
        leftPanel.setBackground(new Color(0xF2E9E4));
        leftPanel.setBounds(30, 35, 350, 460);
        panel.add(leftPanel);

        jList = new JList(regList);
        jList.setBackground(new Color(0xF2E9E4));
        jList.setFont(new Font("Verdana", Font.BOLD,15));
        jList.setPreferredSize(new Dimension(330,440));
        leftPanel.add(jList);

        profileDetailsButton = new JButton();
        profileDetailsButton.setBounds(390, 40, 140, 30);
        profileDetailsButton.setFont(new Font("Calibri", Font.BOLD, 15));
        profileDetailsButton.setBackground(new Color(0x9A8C98));
        profileDetailsButton.setForeground(Color.white);
        profileDetailsButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        panel.add(profileDetailsButton);

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

        acceptButton = new JButton();
        acceptButton.setBounds(395, 170, 130, 80);
        acceptButton.setFont(new Font("Verdana", Font.BOLD, 20));
        acceptButton.setBackground(new Color(0x51D200));
        acceptButton.setForeground(Color.white);
        acceptButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        panel.add(acceptButton);

        rejectButton = new JButton();
        rejectButton.setBounds(395, 270, 130, 80);
        rejectButton.setFont(new Font("Verdana", Font.BOLD, 20));
        rejectButton.setBackground(new Color(0xBD001B));
        rejectButton.setForeground(Color.white);
        rejectButton.setUI(new client_package.ClientGUI.StyledButtonUI());
        panel.add(rejectButton);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.add(panel);
        this.setSize(550, 570);
        this.pack();
        this.setVisible(true);
        this.setLocation(400, 50);

        profileDetailsButton.setText("PROFILE DETAILS");
        profileDetailsButton.addActionListener(e -> {
            String employee = regList[jList.getSelectedIndex()];
            int userIndex = registrationList.stream().map(User::getUsername).toList().indexOf(employee);
            User user = registrationList.get(userIndex);
            new ProfileDetailsGUIS(user);
        });

        acceptButton.setText("ACCEPT");
        acceptButton.addActionListener(evt -> {
            String employee = regList[jList.getSelectedIndex()];
            int userIndex = registrationList.stream().map(User::getUsername).toList().indexOf(employee);
            User user = registrationList.get(userIndex);
            EmployeeDetailsManager employeeDetails = new EmployeeDetailsManager(user.getEmployeeDetails().getEmployeeID(),
                    user.getEmployeeDetails().getEmployeeContactNo(), user.getEmployeeDetails().getEmployeeEmergencyContact(),
                    user.getEmployeeDetails().getEmployeeName(), user.getEmployeeDetails().getEmployeePosition(),
                    user.getEmployeeDetails().getEmployeeBirthday(), user.getEmployeeDetails().getEmployeeAddress(),
                    user.getEmployeeDetails().getEmployeeSex(), user.getEmployeeDetails().getEmployeeDepartment(),
                    user.getUsername(), user.getPassword());
            try {
                employeeDetails.createEmployee("employee");
                new RegistrationRequestsGUI(server, buttonFromMenu);
                this.dispose();
            } catch (InvalidCredentialsException e) {
                JOptionPane.showMessageDialog(null, "There was an error. Please try again.",
                        "Accept Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        rejectButton.setText("REJECT");
        rejectButton.addActionListener(evt -> {
            String employee = regList[jList.getSelectedIndex()];
            int userIndex = registrationList.stream().map(User::getUsername).toList().indexOf(employee);
            User user = registrationList.get(userIndex);
            EmployeeDetailsManager employeeDetails = new EmployeeDetailsManager(user.getEmployeeDetails().getEmployeeID(),
                    user.getEmployeeDetails().getEmployeeContactNo(), user.getEmployeeDetails().getEmployeeEmergencyContact(),
                    user.getEmployeeDetails().getEmployeeName(), user.getEmployeeDetails().getEmployeePosition(),
                    user.getEmployeeDetails().getEmployeeBirthday(), user.getEmployeeDetails().getEmployeeAddress(),
                    user.getEmployeeDetails().getEmployeeSex(), user.getEmployeeDetails().getEmployeeDepartment(),
                    user.getUsername(), user.getPassword());
            employeeDetails.deleteRegistration();
            new RegistrationRequestsGUI(server, buttonFromMenu);
            this.dispose();
        });

        backButton.setText("BACK");
        backButton.addActionListener(evt -> {
            buttonFromMenu.setEnabled(true);
            buttonFromMenu.setBackground(new Color(0xF2E9E4));
            dispose();
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
