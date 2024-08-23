package interfaces;

import common_package.Exceptions.NoAccountFoundException;
import common_package.Exceptions.InvalidCredentialsException;
import common_package.objects.EmployeeAttendance;
import common_package.objects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeInTimeOut extends Remote{
    User login(String username, String password) throws RemoteException, NoAccountFoundException;

    void register(int employeeID, String employeeContactNo, String employeeEmergencyContact,
                  String employeeName, String employeePosition, String employeeBirthday,
                  String employeeAddress, String employeeSex, String employeeDepartment,
                  String username, String password)
            throws RemoteException, InvalidCredentialsException;

    LocalDateTime timeIn(int id) throws RemoteException;

    LocalDateTime timeOut(int id) throws RemoteException;

    List<List<String>> getSummary(int id) throws RemoteException;

    String getStatus(int id) throws RemoteException;

    String getTimeZone() throws RemoteException;
}
