package server_package;

import common_package.Exceptions.NoAccountFoundException;
import common_package.objects.User;
import interfaces.TimeInTimeOut;
import common_package.Exceptions.InvalidCredentialsException;
import server_package.server_classes.EmployeeDetailsManager;
import server_package.server_classes.AttendanceManager;
import server_package.server_classes.LoginManager;
import common_package.objects.EmployeeAttendance;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

public class Servant extends UnicastRemoteObject implements TimeInTimeOut {
    protected Servant() throws RemoteException {
    }

    /**
     * This method will execute the verifyCredentials method of the LoginManger class to execute the codes which handles
     * login action.
     * @param username entered by the user
     * @param password entered by the user
     * @return User object pertaining to the user who log in
     * @throws RemoteException
     * @throws NoAccountFoundException
     */
    @Override
    public User login(String username, String password) throws RemoteException, NoAccountFoundException {
        LoginManager loginManager = new LoginManager(username, password);
        User user = loginManager.verifyCredentials();
        if(user == null){
            throw new NoAccountFoundException("Invalid Username/Password");
        } else {
            return user;
        }
    }

    /**
     * This method will execute the createEmployee method of the EmployeeDetailsManager class in order to execute the codes
     * regarding the registration of a user
     * @param employeeID entered by the user
     * @param employeeContactNo entered by the user
     * @param employeeEmergencyContact entered by the user
     * @param employeeName entered by the user
     * @param employeePosition entered by the user
     * @param employeeBirthday entered by the user
     * @param employeeAddress entered by the user
     * @param employeeSex entered by the user
     * @param employeeDepartment entered by the user
     * @param username entered by the user
     * @param password entered by the user
     * @throws RemoteException
     * @throws InvalidCredentialsException
     */
    @Override
    public void register(int employeeID, String employeeContactNo, String employeeEmergencyContact,
                         String employeeName, String employeePosition, String employeeBirthday,
                         String employeeAddress, String employeeSex, String employeeDepartment,
                         String username, String password) throws RemoteException, InvalidCredentialsException {
        EmployeeDetailsManager employeeDetails = new EmployeeDetailsManager(employeeID, employeeContactNo,
                employeeEmergencyContact, employeeName, employeePosition, employeeBirthday, employeeAddress,
                employeeSex, employeeDepartment, username, password);
        String value = employeeDetails.createEmployee("registration");
        if(value != null){
            throw new InvalidCredentialsException(value);
        }
    }

    /**
     * This method executes the processTimeInTimeOut method of the AttendanceManager class in order to execute the
     * following codes in regard of timing in.
     * @param id number of the employee
     * @return date and time of time in
     * @throws RemoteException .
     */
    @Override
    public LocalDateTime timeIn(int id) throws RemoteException {
        AttendanceManager timeIn = new AttendanceManager();
        return timeIn.processTimeInTimeOut(id,"working", "0:0");
    }

    /**
     * This method executes the processTimeInTimeOut method of the AttendanceManager class in order to execute the
     * following codes in regard of timing out.
     * @param id number of the employee
     * @return date and time of time out
     * @throws RemoteException .
     */
    @Override
    public LocalDateTime timeOut(int id) throws RemoteException {
        AttendanceManager timeOut = new AttendanceManager();
        return timeOut.processTimeInTimeOut(id,"break","0:0");
    }

    /**
     * This method executes the employeeAttendance method of the AttendanceManager class in order to execute the following
     * codes in regard to processing the summary
     * @param id
     * @return
     * @throws RemoteException
     */
    @Override
    public List<List<String>> getSummary(int id) throws RemoteException {
        AttendanceManager summary = new AttendanceManager();
        try {
            return summary.employeeAttendance(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method executes the getStatus method of the AttendanceManager class in order to execute the
     * following codes in regard of getting the current status of an employee.
     * @param id number of the employee
     * @return current work status of employee
     * @throws RemoteException ..
     */
    @Override
    public String getStatus(int id) throws RemoteException {
        AttendanceManager status = new AttendanceManager();
        try {
            return status.getStatus(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method retrieves the current time zone where the program/server is being run.
     * @return string time zone
     * @throws RemoteException .
     */
    @Override
    public String getTimeZone() throws RemoteException{
        return TimeZone.getDefault().getID();
    }
}
