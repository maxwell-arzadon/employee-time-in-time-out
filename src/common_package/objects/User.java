package common_package.objects;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private EmployeeDetails employeeDetails;
    private String username;
    private String password;

    public User(EmployeeDetails employeeDetail, String username, String password){
        this.setEmployeeDetails(employeeDetail);
        this.setUsername(username);
        this.setPassword(password);
    }

    public EmployeeDetails getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
