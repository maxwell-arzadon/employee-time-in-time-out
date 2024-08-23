package server_package.ServerGUI;

import server_package.ServerApplication;
import server_package.server_classes.AdminManager;

import javax.swing.*;
import java.awt.*;

public class AdminEditCredentialsGUI extends JFrame {
    private JTextField uname;
    private JPasswordField pword;
    private JLabel username, password;
    private JButton exit, edit;
    private JPanel p1;

    public AdminEditCredentialsGUI(ServerApplication server, JButton updateCredentials){
        updateCredentials.setEnabled(false);
        updateCredentials.setBackground(new Color(0x4A4E69));

        this.setTitle("Admin Credentials");
        this.setSize(350, 205);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.setBackground(new java.awt.Color(0x364E65));
        p1.setPreferredSize(new java.awt.Dimension(380, 270));
        p1.setLayout(null);
        this.add(p1, BorderLayout.NORTH);

        edit = new JButton("Edit");
        exit = new JButton("Exit");

        edit.setBounds(170, 125, 70, 30);
        edit.setBackground(new Color(0xA2ABB4));
        edit.setForeground(new Color(0x12232F));
        edit.setUI(new StyledButtonUI());

        exit.setBounds(90, 125, 70, 30);
        exit.setBackground(new Color(0x83626C));
        exit.setForeground(new Color(0x12232F));
        exit.setUI(new StyledButtonUI());

        username = new JLabel("Username");
        username.setBounds(70, 5, 100, 50);
        username.setFont(new Font("Calibri", Font.PLAIN,15));
        username.setForeground(new Color(0xA2ABB4));
        p1.add(username);

        password = new JLabel("Password");
        password.setBounds(70, 55, 100, 50);
        password.setFont(new Font("Calibri", Font.PLAIN,15));
        password.setForeground(new Color(0xA2ABB4));
        p1.add(password);

        uname = new JTextField();
        uname.setBounds(70, 35, 200, 20);
        p1.add(uname);

        pword = new JPasswordField();
        pword.setBounds(70, 85, 200, 20);
        p1.add(pword);

        p1.add(edit);
        p1.add(exit);

        edit.addActionListener(e -> {
            String name = uname.getText();
            String pass = pword.getText();

            AdminManager adminManager = new AdminManager(name, pass);
            adminManager.editAdminCredentials();
            JOptionPane.showMessageDialog(null, "Username and Password has successfully been updated.",
                    "Edit Successful", JOptionPane.INFORMATION_MESSAGE);
        });

        exit.addActionListener(e -> {
            updateCredentials.setEnabled(true);
            updateCredentials.setBackground(new Color(0xF2E9E4));
            dispose();
        });

        this.setVisible(true);
    }
}
