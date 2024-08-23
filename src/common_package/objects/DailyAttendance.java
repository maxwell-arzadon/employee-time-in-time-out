package common_package.objects;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailyAttendance implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private String date = "";
    private String totalTimeRendered = "";
    private List<EmployeeAttendance> attendanceList = new ArrayList<>();

    public DailyAttendance(){

    }

    public DailyAttendance(String date,String totalTimeRendered, List<EmployeeAttendance> attendanceList){
        this.date = date;
        this.totalTimeRendered = totalTimeRendered;
        this.attendanceList = attendanceList;
    }

    public DailyAttendance(String date, List<EmployeeAttendance> employeeAttendanceList) {
    }

    public String getDate() {
        return date;
    }

     public String getTotalTimeRendered() {
        return totalTimeRendered;
    }

    public List<EmployeeAttendance> getAttendanceList() {
        return attendanceList;
    }

    public void setTotalTimeRendered(String totalTimeRendered) {
        this.totalTimeRendered = totalTimeRendered;
    }
}
