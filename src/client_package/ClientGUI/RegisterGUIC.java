package client_package.ClientGUI;


import client_package.ClientApplication;
import common_package.Exceptions.InvalidCredentialsException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

public class RegisterGUIC extends JFrame implements Serializable {
    public JLabel title,getEmployeeName,display,ID,uname,pass,
            firstname,lastname,contactnum,emergencycon,position,
            department,bday,address,sex;
    public ImageIcon background;
    public JTextField username,empID,fname,lname,cnum,ecnum,pos,dept,addr;
    public JPasswordField password;
    public JCheckBox c1,c2;
    public JComboBox years,months,dates;
    public JButton confirm,back;
    public Font customFont;

    public RegisterGUIC(ClientApplication client) {
        try {
            background = new ImageIcon(Objects.requireNonNull(getClass().getResource("ClientGUIResources/background.jpg")));
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

        sex= new JLabel("Sex");
        sex.setBounds(80, 390, 300,25);
        c1=new JCheckBox("MALE");
        c1.setBounds(80,410,100,30);
        c1.setBackground(new Color(0xC9ADA7));
        c2=new JCheckBox("FEMALE");
        c2.setBackground(new Color(0xC9ADA7));
        c2.setBounds(200,410,100,30);
        display.add(c1);
        display.add(c2);
        sex.setForeground(new Color(0xC9ADA7));
        display.add(sex);

        String[] dayList = new String[32];
        for(int i = 1; i < dayList.length; i++){
            dayList[i] = String.valueOf(i);
        }
        dates = new JComboBox(dayList);
        dates.setFont(new Font("Arial", Font.PLAIN, 15));
        dates.setSize(50, 20);
        dates.setLocation(80, 370);
        dates.setBackground(new Color(0xC9ADA7));
        display.add(dates);

        String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        months = new JComboBox(monthList);
        months.setFont(new Font("Arial", Font.PLAIN, 15));
        months.setSize(50, 20);
        months.setLocation(160, 370);
        months.setBackground(new Color(0xC9ADA7));
        display.add(months);

        String[] yearList = new String[123];
        int year = 1900;
        for(int i = 0; i < 123; i++){
            yearList[i] = String.valueOf(year);
            year++;
        }
        years = new JComboBox(yearList);
        years.setFont(new Font("Arial", Font.PLAIN, 15));
        years.setSize(70, 20);
        years.setLocation(240, 370);
        years.setBackground(new Color(0xC9ADA7));
        display.add(years);

        title = new JLabel("KRONOS");
        title.setBounds(280, 5, 300,80);
        title.setFont(customFont.deriveFont(70f));
        title.setForeground(new Color(0xfeea86));
        display.add(title);

        getEmployeeName = new JLabel("REGISTER");
        getEmployeeName.setBounds(380, 62, 300,50);
        getEmployeeName.setFont(customFont);
        getEmployeeName.setForeground(new Color(0xC9ADA7));
        display.add(getEmployeeName);

        firstname = new JLabel("Firstname");
        firstname.setBounds(80, 110, 100,25);
        firstname.setForeground(new Color(0xFFFFFF));
        fname = new JTextField();
        fname.setBounds(80, 130, 300,25);
        fname.setBackground(new Color(0xC9ADA7));
        display.add(firstname);
        display.add(fname);

        lastname = new JLabel("Lastname");
        lastname.setBounds(420, 110, 100,25);
        lastname.setForeground(new Color(0xFFFFFF));
        lname = new JTextField();
        lname.setBounds(420, 130, 300,25);
        lname.setBackground(new Color(0xC9ADA7));
        display.add(lastname);
        display.add(lname);

        ID = new JLabel("User ID");
        ID.setBounds(420, 150, 300,25);
        ID.setForeground(new Color(0xFFFFFF));
        empID = new JTextField();
        empID.setBounds(420, 170, 300,25);
        empID.setBackground(new Color(0xC9ADA7));
        display.add(ID);
        display.add(empID);

        uname = new JLabel("Username");
        uname.setBounds(80, 150, 300,25);
        uname.setForeground(new Color(0xFFFFFF));
        username = new JTextField();
        username.setBounds(80, 170, 300,25);
        username.setBackground(new Color(0xC9ADA7));
        display.add(uname);
        display.add(username);

        pass = new JLabel("Password");
        pass.setBounds(80, 190, 300,25);
        pass.setForeground(new Color(0xFFFFFF));
        password = new JPasswordField();
        password.setBounds(80, 210, 300,25);
        password.setBackground(new Color(0xC9ADA7));
        display.add(pass);
        display.add(password);

        contactnum = new JLabel("Contact Number");
        contactnum.setBounds(80, 230, 300,25);
        contactnum.setForeground(new Color(0xFFFFFF));
        cnum = new JTextField();
        cnum.setBounds(80, 250, 300,25);
        cnum.setBackground(new Color(0xC9ADA7));
        display.add(contactnum);
        display.add(cnum);

        emergencycon = new JLabel("Emergency Contact Number");
        emergencycon.setBounds(420, 230, 300,25);
        emergencycon.setForeground(new Color(0xFFFFFF));
        ecnum = new JTextField();
        ecnum.setBounds(420, 250, 300,25);
        ecnum.setBackground(new Color(0xC9ADA7));
        display.add(emergencycon);
        display.add(ecnum);

        position = new JLabel("Position");
        position.setBounds(420, 270, 300,25);
        position.setForeground(new Color(0xFFFFFF));
        pos = new JTextField();
        pos.setBounds(420, 290, 300,25);
        pos.setBackground(new Color(0xC9ADA7));
        display.add(position);
        display.add(pos);

        department = new JLabel("Department");
        department.setBounds(80, 270, 300,25);
        department.setForeground(new Color(0xFFFFFF));
        dept = new JTextField();
        dept.setBounds(80, 290, 300,25);
        dept.setBackground(new Color(0xC9ADA7));
        display.add(department);
        display.add(dept);

        address = new JLabel("Address");
        address.setBounds(80, 310, 300,25);
        address.setForeground(new Color(0xFFFFFF));
        addr = new JTextField();
        addr.setBounds(80, 330, 300,25);
        addr.setBackground(new Color(0xC9ADA7));
        display.add(address);
        display.add(addr);

        bday = new JLabel("Birthday (D/M/Y)");
        bday.setBounds(80, 350, 300,25);
        bday.setForeground(new Color(0xFFFFFF));

        display.add(bday);

        confirm = new JButton("Confirm");
        confirm.setBounds(420, 420, 130,30);
        confirm.setFont(new Font("Calibri", Font.BOLD, 15));
        confirm.setBackground(new Color(0xF2E9E4));
        confirm.setForeground(new Color(0x4A4E69));
        confirm.setUI(new client_package.ClientGUI.StyledButtonUI());
        confirm.addActionListener(e -> {
            String firstnameText = fname.getText();
            String lastnameText = lname.getText();
            String fullName = firstnameText + " " + lastnameText;

            String position = pos.getText();
            String department = dept.getText();
            String name = username.getText();
            String passw = password.getText();

            String yrs, mnth, day;
            yrs = String.valueOf(years.getSelectedItem());
            mnth = String.valueOf(months.getSelectedItem());
            day = String.valueOf(dates.getSelectedItem());

            boolean acceptedDate = validateDate(yrs, mnth, day);
            
            String birthday = yrs + "/" + mnth + "/" + day;
            String address = addr.getText();

            String sex = null;
            boolean acceptedSex = true;
            if(c1.isSelected() && c2.isSelected()){
                acceptedSex = false;
            } else if (!c1.isSelected() && !c2.isSelected()){
                acceptedSex = false;
            } else if (c1.isSelected()){
                sex = c1.getText();
            } else if (c2.isSelected()){
                sex = c2.getText();
            }

            String idNum = empID.getText();
            String contactNo = cnum.getText();
            String emergencyContact = ecnum.getText();
            boolean acceptedID = validateID(idNum);
            boolean acceptedCN = validateContactNo(contactNo);
            boolean acceptedECN = validateContactNo(emergencyContact);

            if (acceptedID && acceptedCN && acceptedECN && acceptedSex && acceptedDate) {
                try{
                    client.runClient();
                    int idNumber = Integer.parseInt(idNum);
                    client.register(idNumber, contactNo, emergencyContact, fullName,
                            position, birthday, address, sex, department,
                            name, passw);
                    new LogInGUIC(client);
                    this.dispose();
                } catch (InvalidCredentialsException ic){
                    JOptionPane.showMessageDialog(null, ic.getMessage(),
                            "Registration Failed", JOptionPane.ERROR_MESSAGE);
                } catch (RemoteException | NotBoundException ex) {
                    JOptionPane.showMessageDialog(null, "Server is Turned Off, Try Again",
                            "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }

            } else if (!acceptedID) {
                JOptionPane.showMessageDialog(null, "Invalid ID number" , "Registration Failed", JOptionPane.ERROR_MESSAGE);
            } else if (!acceptedDate){
                JOptionPane.showMessageDialog(null, "Invalid Date", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            } else if (!acceptedSex){
                JOptionPane.showMessageDialog(null, "Invalid Sex", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Contact Number (Input your 11 digit number starting with 0)", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        back = new JButton("Back");
        back.setBounds(590, 420, 130,30);
        back.setFont(new Font("Calibri", Font.BOLD, 15));
        back.setForeground(new Color(0xF2E9E4));
        back.setBackground(new Color(0x4A4E69));
        back.setUI(new client_package.ClientGUI.StyledButtonUI());
        back.addActionListener(evt -> {
            new LogInGUIC(client);
            dispose();
        });

        display.add(confirm);
        display.add(back);

        this.setTitle("KRONOS: Time In & Time Out Application (Client)");
        this.add(display);
        setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private static boolean validateDate(String yrs, String mnth, String days){
        int year = Integer.parseInt(yrs);
        int day = Integer.parseInt(days);
        int month = Integer.parseInt(mnth);

        switch (month){
            case 2 -> {
                if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
                    if (day <= 29){
                        return true;
                    }
                } else {
                    if(day <= 28){
                        return true;
                    }
                }
            }
            case 4, 6, 9, 10 -> {
                if(day <= 30){
                    return true;
                }
            }
            default -> {
                if(day <= 31) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean validateContactNo(String contact){
        long num;
        try{
            num = Long.parseLong(contact);
        } catch (NumberFormatException nfe) {
            return false;
        }
        int count = 0;
        while(num!= 0){
            num = num/10;
            count++;
        }
        return count == 10;
    }

    private static boolean validateID(String employeeID){
        int id;
        try{
            id = Integer.parseInt(employeeID);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

