package common_package.objects;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class EmployeeAttendance implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private int id;
    private String status;
    private String hoursWorked;

    private List<String> timeOut;
    private List<String> timeIn;
    private String date;

    public EmployeeAttendance(int id, String status,String hoursWorked, List<String>timeIn, List<String>timeOut ){
        this.id = id;
        this.status = status;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTimeOut() {
        return timeOut;
    }

    public List<String> getTimeIn() {
        return timeIn;
    }

    public String getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setTimeIn(List<String> timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(List<String> timeInTimeOut) {
        this.timeOut = timeInTimeOut;
    }
}
