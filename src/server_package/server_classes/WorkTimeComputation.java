package server_package.server_classes;

import com.google.gson.reflect.TypeToken;
import common_package.objects.DailyAttendance;
import server_package.tools.JSONReaderWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorkTimeComputation {
    public static String [] timeInArray, timeOutArray, totalTime;
    public static int startHour, startMinutes, endHour, endMinutes;
    public static ArrayList<Integer> totalHours = new ArrayList<>();
    public static ArrayList<Integer> totalMinutes = new ArrayList<>();
    static ArrayList <String> listOfDates = new ArrayList<>();

    public WorkTimeComputation(){
    }

    /**
     * This method sets the Hours and the Minutes of a given time.
     * @param time is the time to be converted.
     */
    public void setHourAndMinute(String time){
        totalTime = time.split(":");
        totalHours.add(Integer.parseInt(totalTime[0]));
        totalMinutes.add(Integer.parseInt(totalTime[1]));
    }

    /**
     * Compute the difference of time-in and time-out.
     * @param timeIn is the Employee's time-in.
     * @param timeOut is the Employee's time-out.
     */
   public void computeTimeDiff(String timeIn, String timeOut){
        timeInArray = timeIn.split(":");
        timeOutArray = timeOut.split(":");


        for (int i = 0; i < timeInArray.length; i++){
            if (i == 0) {
                startHour = Integer.parseInt(timeInArray[i]);
                endHour = Integer.parseInt(timeOutArray[i]);
            } else {
                startMinutes = Integer.parseInt(timeInArray[i]);
                endMinutes = Integer.parseInt(timeOutArray[i]);
            }
        }

        TimeDifference start = new TimeDifference(startHour, startMinutes);
        TimeDifference end = new TimeDifference(endHour, endMinutes);
        TimeDifference diff;

        diff = TimeDifference.difference(start, end);
        totalHours.add(diff.hours);
        totalMinutes.add(diff.minutes);
    }

     /**
     * This method populates the listOfDates arrayList based from the given range of dates.
     * The date from the beginning to end will be added to listOfDates.
     * @param startDate is the starting date.
     * @param endDate is the ending date.
     * @return the updated listOfDates.
     * @throws IOException for reading or writing in the JSON file.
     */
    public static ArrayList<String> populateListOfDates(String startDate, String endDate) throws IOException {
        Type type = new TypeToken<List<DailyAttendance>>(){}.getType();

        JSONReaderWriter<DailyAttendance> jsonReaderWriter = new JSONReaderWriter<>();
        List<DailyAttendance> attendanceList =jsonReaderWriter.
                readJSONFile("src\\server_package\\json_files\\attendance.json", type);

        String tempDate = "";
        boolean flag = false;

        for (int i = 0; i < attendanceList.size(); i++) {
            tempDate = attendanceList.get(i).getDate();
            if (tempDate.equals(startDate)) {
                flag = true;
            }
            while (flag) {
                tempDate = attendanceList.get(i).getDate();
                listOfDates.add(tempDate);
                i++;
                if (tempDate.equals(endDate)) {
                    return listOfDates;
                }
            }
        }
        return listOfDates;
    }

    /**
     * This method updates the totalHours and totalMinutes arraylist from the given
     * range of dates for totalComputation. The populateListOfDates has been invoked here for
     * updating with the help of startDate and endDate parameters.
     * method
     * @param startDate is the starting date.
     * @param endDate is the ending date.
     * @throws IOException for reading or writing in the JSON file.
     */
    public static void generateTotalSum(String startDate, String endDate) throws IOException {
        String[] totalHr;
        Type type = new TypeToken<List<DailyAttendance>>(){}.getType();
        boolean flag = false;
        JSONReaderWriter<DailyAttendance> jsonReaderWriter = new JSONReaderWriter<>();
        List<DailyAttendance> attendanceList =jsonReaderWriter.readJSONFile("src\\server_package\\json_files\\attendance.json", type);
        populateListOfDates(startDate,endDate);
        totalHours.clear();
        totalMinutes.clear();
        for (int i = 0; i < attendanceList.size(); i++) {
            if (attendanceList.get(i).getDate().equals(startDate)){
                flag = true;
            }
            while (flag) {
                String tempDate = attendanceList.get(i).getDate();
                if (attendanceList.get(i).getDate().equals(attendanceList.get(i).getDate())) {
                    totalHr = attendanceList.get(i).getTotalTimeRendered().split(":");
                    for (int k = 0; k < totalHr.length; k++) {
                        if (k == 0) {
                            totalHours.add(Integer.parseInt(totalHr[k]));
                        } else {
                            totalMinutes.add(Integer.parseInt(totalHr[k]));
                        }
                    }

                }
                i++;
                if (tempDate.equals(endDate)){
                    break;
                }
            }
        }
    }

     /**
     * Creates a list of employee attendance in a given date.
     * @param date date to be checked for employee attendance
     * @return An ArrayList of employees who timed in for a specific date.
     * @throws IOException for reading or writing in the JSON file.
     */
    public static ArrayList<String> getEmployeeAttendancePerDay(String date) throws IOException {
        ArrayList<String> empList = new ArrayList<>();
        Type type = new TypeToken<List<DailyAttendance>>(){}.getType();

        JSONReaderWriter<DailyAttendance> jsonReaderWriter = new JSONReaderWriter<>();
        List<DailyAttendance> attendanceList =jsonReaderWriter.
                readJSONFile("src\\server_package\\json_files\\attendance.json", type);
        String user = "";
        for (DailyAttendance dailyAttendance : attendanceList) {
            if (dailyAttendance.getDate().equals(date)) {
                for (int j = 0; j < dailyAttendance.getAttendanceList().size(); j++) {
                    user = String.valueOf(dailyAttendance.getAttendanceList().get(j).getId());
                    empList.add(user);
                }
            }
        }
        return empList;
    }

    /**
     * Creates a list of time rendered by employees in a given date.
     * @param date is the date of the employee.
     * @return the timeWorked.
     * @throws IOException for reading or writing in the JSON file.
     */
    public static ArrayList<String> getTimeRenderedPerDay(String date) throws IOException {
        ArrayList<String> timeWorked = new ArrayList<>();
        ArrayList<String> empList = getEmployeeAttendancePerDay(date);
        Type type = new TypeToken<List<DailyAttendance>>(){}.getType();

        JSONReaderWriter<DailyAttendance> jsonReaderWriter = new JSONReaderWriter<>();
        List<DailyAttendance> attendanceList =jsonReaderWriter.
                readJSONFile("src\\server_package\\json_files\\attendance.json", type);
        String user = "";
        for (int i = 0; i < empList.size(); i++){
            user = empList.get(i);
            for (DailyAttendance dailyAttendance : attendanceList){
                if (dailyAttendance.getDate().equals(date)){
                    for (int j = 0; j < dailyAttendance.getAttendanceList().size(); j++ ){
                        if ((String.valueOf(dailyAttendance.getAttendanceList().get(j).getId())).equals(user)){
                            timeWorked.add(dailyAttendance.getAttendanceList().get(j).getHoursWorked());
                        }
                    }
                }
            }
        }
        return timeWorked;

    }

    /**
     * This method sums all the elements of totalHours and totalMinutes.
     * This method updates the hours once the condition in computing
     * the minutes has been fulfilled.
     * @return the String containing the hours and minutes.
     */
    public static String totalComputation(){
        int hours = 0,minutes = 0, add = 0;
        for (int i : totalMinutes){
            minutes += i;
            if (minutes >= 60){
                hours += 1;
                minutes -= 60;

            }
        }
        for (int i : totalHours){
            hours += i;
        }
        totalHours.clear();
        totalMinutes.clear();
        return hours+":"+minutes;
    }
}
