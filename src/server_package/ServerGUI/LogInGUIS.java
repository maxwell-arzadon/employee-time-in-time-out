package server_package.ServerGUI;

import server_package.ServerApplication;
import server_package.server_classes.AdminManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LogInGUIS extends JFrame {
    public JLabel title, subTitle,display,ID,uname,pass;
    public ImageIcon background;
    public JTextField username;
    public JPasswordField password;
    public JButton login;
    public Font customFont;

    public LogInGUIS(ServerApplication server) {
        try {
            background = new ImageIcon(Objects.requireNonNull(getClass().getResource("ServerGUIResources\\backg.png")));
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

        title = new JLabel("KRONOS");
        title.setBounds(470, 70, 300, 70);
        title.setFont(customFont.deriveFont(70f));
        title.setForeground(new Color(0xfeea86));
        display.add(title);

        subTitle = new JLabel("ADMIN",SwingConstants.CENTER);
        subTitle.setBounds(470, 120, 300,50);
        subTitle.setFont(customFont);
        subTitle.setForeground(new Color(0x979CAD));
        display.add(subTitle);

        uname = new JLabel("Username");
        uname.setBounds(420, 180, 300,25);
        uname.setForeground(new Color(0x979CAD));
        username = new JTextField();
        username.setBounds(420, 200, 300,25);
        username.setBackground(new Color(0x979CAD));
        display.add(uname);
        display.add(username);

        pass = new JLabel("Password");
        pass.setBounds(420, 230, 300,25);
        pass.setForeground(new Color(0x979CAD));

        password = new JPasswordField();
        password.setBounds(420, 250, 300,25);
        password.setBackground(new Color(0x979CAD));
        display.add(pass);
        display.add(password);

        login = new JButton("Login");
        login.setBounds(420, 300, 300,30);
        login.setFont(new Font("Calibri", Font.BOLD, 15));
        login.setBackground(new Color(0xF2E9E4));
        login.setForeground(new Color(0x4A4E69));
        login.setUI(new client_package.ClientGUI.StyledButtonUI());
        login.addActionListener(evt -> {
            String name = username.getText();
            String pass = password.getText();

            AdminManager adminManager = new AdminManager(name, pass);
            if(adminManager.validateAdmin()){
                server.startServer();
                new AdminMenuGUI(server);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username/Password",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        display.add(login);

        this.setTitle("KRONOS: Time In & Time Out Application (Server)");
        this.add(display);
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}

