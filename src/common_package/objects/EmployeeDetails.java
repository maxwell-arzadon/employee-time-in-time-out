package common_package.objects;

import java.io.Serial;
import java.io.Serializable;

public class EmployeeDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private int employeeID;
    private String employeeContactNo;
    private String employeeEmergencyContact;
    private String employeeName;
    private String employeePosition;
    private String employeeBirthday;
    private String employeeAddress;
    private String employeeSex;
    private String employeeDepartment;

    public EmployeeDetails(int employeeID, String employeeContactNo, String employeeEmergencyContact,
                           String employeeName, String employeePosition, String employeeBirthday,
                           String employeeAddress, String employeeSex, String employeeDepartment){
        this.setEmployeeID(employeeID);
        this.setEmployeeContactNo(employeeContactNo);
        this.setEmployeeEmergencyContact(employeeEmergencyContact);
        this.setEmployeeName(employeeName);
        this.setEmployeePosition(employeePosition);
        this.setEmployeeBirthday(employeeBirthday);
        this.setEmployeeAddress(employeeAddress);
        this.setEmployeeSex(employeeSex);
        this.setEmployeeDepartment(employeeDepartment);
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeContactNo() {
        return employeeContactNo;
    }

    public void setEmployeeContactNo(String employeeContactNo) {
        this.employeeContactNo = employeeContactNo;
    }

    public String getEmployeeEmergencyContact() {
        return employeeEmergencyContact;
    }

    public void setEmployeeEmergencyContact(String employeeEmergencyContact) {
        this.employeeEmergencyContact = employeeEmergencyContact;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
}
