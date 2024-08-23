package client_package.ClientGUI;

import client_package.ClientApplication;
import common_package.Exceptions.NoAccountFoundException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LogInGUIC extends JFrame{
    public JLabel title, subTitle, display, uname, pass;
    public ImageIcon background;
    public JTextField username;
    public JPasswordField password;
    public JButton login, register;
    public Font customFont;

    public LogInGUIC(ClientApplication client) {
        try {
            background = new ImageIcon(getClass().getResource("ClientGUIResources/backg.png"));
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

        subTitle = new JLabel("CLIENT",SwingConstants.CENTER);
        subTitle.setBounds(460, 120, 300,50);
        subTitle.setFont(customFont);
        subTitle.setForeground(new Color(0x979CAD));
        display.add(subTitle);

        uname = new JLabel("Username");
        uname.setBounds(420, 180, 300, 25);
        uname.setForeground(new Color(0xC9ADA7));
        username = new JTextField();
        username.setBounds(420, 200, 300, 25);
        username.setBackground(new Color(0xC9ADA7));
        display.add(uname);
        display.add(username);

        pass = new JLabel("Password");
        pass.setBounds(420, 230, 300, 25);
        pass.setForeground(new Color(0xC9ADA7));

        password = new JPasswordField();
        password.setBounds(420, 250, 300, 25);
        password.setBackground(new Color(0xC9ADA7));
        display.add(pass);
        display.add(password);

        login = new JButton("Login");
        login.setBounds(420, 300, 130, 30);
        login.setFont(new Font("Calibri", Font.BOLD, 15));
        login.setBackground(new Color(0xF2E9E4));
        login.setForeground(new Color(0x4A4E69));
        login.setUI(new StyledButtonUI());
        login.addActionListener(e -> {
            String name = username.getText();
            String pass = password.getText();

            try {
                client.runClient();
                client.login(name, pass);
                new TimeInTimeOutGUI(client);
                dispose();
            } catch (NoAccountFoundException anf){
                JOptionPane.showMessageDialog(null, anf.getMessage(),
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }  catch (RemoteException | NotBoundException ex) {
                JOptionPane.showMessageDialog(null, "Server is Turned Off, Try Again",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        register = new JButton("Register");
        register.setBounds(590, 300, 130, 30);
        register.setFont(new Font("Calibri", Font.BOLD, 15));
        register.setForeground(new Color(0xF2E9E4));
        register.setBackground(new Color(0x4A4E69));
        register.setUI(new StyledButtonUI());
        register.addActionListener(evt -> {
            new RegisterGUIC(client);
            dispose();
        });

        display.add(login);
        display.add(register);


        this.setTitle("KRONOS: Time In & Time Out Application (Client)");
        this.add(display);
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}

