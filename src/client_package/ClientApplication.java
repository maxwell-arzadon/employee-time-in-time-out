package client_package;

import client_package.ClientGUI.LogInGUIC;
import common_package.objects.User;
import interfaces.TimeInTimeOut;
import common_package.Exceptions.NoAccountFoundException;
import common_package.Exceptions.InvalidCredentialsException;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.List;

public class ClientApplication implements Serializable {

    private User user;
    private Registry registry;
    static TimeInTimeOut timeInTimeOut;

    public static void main(String[] args){
        ClientApplication client = new ClientApplication();
        new LogInGUIC(client);
    }

    public User getUser() {
        return user;
    }

    public void runClient() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry("localhost", 1900);
        timeInTimeOut = (TimeInTimeOut) registry.lookup("TimeInTimeOutServer");
    }

    public void login(String username, String password) throws RemoteException, NoAccountFoundException {
        user = timeInTimeOut.login(username,password);
    }


    public void register(int employeeID, String employeeContactNo, String employeeEmergencyContact,
                                String employeeName, String employeePosition, String employeeBirthday,
                                String employeeAddress, String employeeSex, String employeeDepartment,
                                String username, String password) throws RemoteException, InvalidCredentialsException{

        timeInTimeOut.register(employeeID, employeeContactNo, employeeEmergencyContact, employeeName,
                employeePosition, employeeBirthday, employeeAddress, employeeSex, employeeDepartment,
                username, password);

    }


    public LocalDateTime timeIn () throws RemoteException {
        return timeInTimeOut.timeIn(user.getEmployeeDetails().getEmployeeID()); // return the date and time the client has timed in
    }

    public LocalDateTime timeOut () throws RemoteException {
        return timeInTimeOut.timeOut(user.getEmployeeDetails().getEmployeeID()); // return the date and time the client has timed out
    }

    public List<List<String>> getSummary() throws RemoteException {
        return timeInTimeOut.getSummary(user.getEmployeeDetails().getEmployeeID());
    }

    public String getStatus() throws RemoteException {
        return timeInTimeOut.getStatus(user.getEmployeeDetails().getEmployeeID());
    }

    public String getTimeZone() throws RemoteException{
        return timeInTimeOut.getTimeZone();
    }

}
