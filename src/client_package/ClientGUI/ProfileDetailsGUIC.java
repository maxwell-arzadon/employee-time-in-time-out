package client_package.ClientGUI;

import client_package.ClientApplication;
import common_package.objects.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class ProfileDetailsGUIC extends JFrame{
    public JPanel panel;
    public JPanel newPanel;
    public JButton backButton;
    public JButton closeAppButton;
    public JLabel employeeID;
    public JLabel contactNo;
    public JLabel emergencyContactNo;
    public JLabel name;
    public JLabel position;
    public JLabel birthday;
    public JLabel address;
    public JLabel sex;
    public JLabel department;

    public Font customFont;

    public ProfileDetailsGUIC(ClientApplication client) {
        User user = client.getUser();
        this.setTitle("KRONOS: Profile Details");
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
                BorderFactory.createEtchedBorder(), "PROFILE DETAILS",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(customFont.getAttributes()), new Color(0xF2E9E4)));
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x4A4E69));
        panel.setPreferredSize(new Dimension(550, 420));
        panel.setLayout(null);

        newPanel = new RoundedPanels();
        newPanel.setBackground(new Color(0xF2E9E4));
        newPanel.setBounds(30, 35, 490, 320);
        newPanel.setLayout(null);
        panel.add(newPanel);

        name = new JLabel(user.getEmployeeDetails().getEmployeeName());
        name.setBounds(12, 5, 700, 40);
        name.setFont(customFont.deriveFont(30f));
        name.setForeground(Color.black);
        newPanel.add(name);

        employeeID = new JLabel("Employee ID %27s%5s".formatted(":",user.getEmployeeDetails().getEmployeeID()));
        employeeID.setBounds(15, 50, 700, 25);
        employeeID.setFont(new Font("Verdana", Font.BOLD, 12));
        employeeID.setForeground(Color.black);
        newPanel.add(employeeID);

        position = new JLabel("Position %35s%5s".formatted(":",user.getEmployeeDetails().getEmployeePosition()));
        position.setBounds(15, 80, 700, 25);
        position.setFont(new Font("Verdana", Font.BOLD, 12));
        position.setForeground(Color.black);
        newPanel.add(position);

        department = new JLabel("Department %29s%5s".formatted(":", user.getEmployeeDetails().getEmployeeDepartment()));
        department.setBounds(15, 110, 700, 25);
        department.setFont(new Font("Verdana", Font.BOLD, 12));
        department.setForeground(Color.black);
        newPanel.add(department);

        contactNo = new JLabel("Contact Number %22s%5s".formatted(":", user.getEmployeeDetails().getEmployeeContactNo()));
        contactNo.setBounds(15, 140, 700, 25);
        contactNo.setFont(new Font("Verdana", Font.BOLD, 12));
        contactNo.setForeground(Color.black);
        newPanel.add(contactNo);

        emergencyContactNo = new JLabel("Emergency Contact Number %3s%5s".formatted(":", user.getEmployeeDetails().getEmployeeEmergencyContact()));
        emergencyContactNo.setBounds(15, 170, 700, 25);
        emergencyContactNo.setFont(new Font("Verdana", Font.BOLD, 12));
        emergencyContactNo.setForeground(Color.black);
        newPanel.add(emergencyContactNo);

        sex = new JLabel("Sex %43s%5s".formatted(":", user.getEmployeeDetails().getEmployeeSex()));
        sex.setBounds(15, 200, 700, 25);
        sex.setFont(new Font("Verdana", Font.BOLD, 12));
        sex.setForeground(Color.black);
        newPanel.add(sex);

        address = new JLabel("Home Address %26s%5s".formatted(":", user.getEmployeeDetails().getEmployeeAddress()));
        address.setBounds(15, 230, 700, 25);
        address.setFont(new Font("Verdana", Font.BOLD, 12));
        address.setForeground(Color.black);
        newPanel.add(address);

        birthday = new JLabel("Birth Date %33s%5s".formatted(":", user.getEmployeeDetails().getEmployeeBirthday()));
        birthday.setBounds(15, 260, 700, 25);
        birthday.setFont(new Font("Verdana", Font.BOLD, 12));
        birthday.setForeground(Color.black);
        newPanel.add(birthday);

        backButton = new JButton();
        backButton.setBounds(200, 370, 100, 30);
        backButton.setFont(new Font("Calibri", Font.BOLD, 15));
        backButton.setBackground(new Color(0x9A8C98));
        backButton.setForeground(Color.white);
        backButton.setUI(new StyledButtonUI());
        panel.add(backButton);

        closeAppButton = new JButton();
        closeAppButton.setBounds(310, 370, 190, 30);
        closeAppButton.setFont(new Font("Calibri", Font.BOLD, 15));
        closeAppButton.setBackground(new Color(0x9A8C98));
        closeAppButton.setForeground(Color.white);
        closeAppButton.setUI(new StyledButtonUI());
        panel.add(closeAppButton);

        this.add(panel);
        this.setSize(550, 420);
        this.setResizable(false);
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
