package server_package.server_classes;

import com.google.gson.reflect.TypeToken;
import common_package.objects.EmployeeDetails;
import common_package.objects.User;
import server_package.tools.JSONReaderWriter;
import common_package.objects.DailyAttendance;
import common_package.objects.EmployeeAttendance;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceManager {
    private String date;
    private String time;
    private LocalDateTime timeDate;
    JSONReaderWriter<DailyAttendance> jsonReaderWriter;

    public AttendanceManager(){
        jsonReaderWriter = new JSONReaderWriter<>();
    }

    /**
     * The readDailyAttendance method utilizes the JSONReaderWriter to extract the Daily Attendance details from the json
     * file and store it in a list.
     * @param jsonReaderWriter object to be used for reading and writing a json file
     * @return list of daily attendance
     * @throws IOException .
     */
    private List<DailyAttendance> readDailyAttendance(JSONReaderWriter<DailyAttendance> jsonReaderWriter) throws IOException {
        Type type = new TypeToken<List<DailyAttendance>>(){}.getType();
        return jsonReaderWriter.readJSONFile("src\\server_package\\json_files\\attendance.json", type);
    }

    /**
     * The processDateAndTime method retrieves the current date and current time of device running the program
     * The method will then format the current date and time and store it in a string variable: date, time
     */
    public void processDateAndTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeDate = LocalDateTime.now();
        date = dateFormatter.format(timeDate);
        time = timeFormatter.format(timeDate);
    }

    /**
     * The processTimeInTimeOut updates an employee's time in or time out. The method will also update the status of
     * the employee depending on the action done by the employee (time in/time out). The method will make use of the
     * JSONReaderWriter class to read and write the updated time in time out of the employee in a json file where the
     * attendance are saved.
     * @param id number of the employee who timed in/out
     * @param status of the employee in terms of time in/out
     * @param hoursWorked empty string
     * @return the date and time the employee has timed in/out
     */
    public LocalDateTime processTimeInTimeOut(int id, String status, String hoursWorked){
        processDateAndTime();
        try {
            List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter); // retrieve data from json file
            int index = dailyAttendanceList.stream().map(DailyAttendance::getDate).toList().indexOf(date); // get index of object with current date

            if(dailyAttendanceList.get(index).getAttendanceList().stream().anyMatch
                    (o -> id==o.getId())){ // find is user has already time in/time out
                int userIndex = dailyAttendanceList.get(index).getAttendanceList().stream().
                        map(EmployeeAttendance::getId).toList().indexOf(id); // get index of existing user
                dailyAttendanceList.get(index).getAttendanceList().get(userIndex).setStatus(status); // set the user status to working/break
                dailyAttendanceList.get(index).getAttendanceList().get(userIndex).setHoursWorked(hoursWorked); //blank total hours per employee

                if(status.equals("working")){
                    dailyAttendanceList.get(index).getAttendanceList().get(userIndex).getTimeIn().add(time); // add time in timeInTimeOut list
                }else{
                    dailyAttendanceList.get(index).getAttendanceList().get(userIndex).getTimeOut().add(time);
                }
            }else{ // if user is has not yet timed in
                List<String>timeInE = new ArrayList<>();
                List<String>timeOutE = new ArrayList<>();
                EmployeeAttendance employee = new EmployeeAttendance(id,status,hoursWorked,timeInE, timeOutE);
                dailyAttendanceList.get(index).getAttendanceList().add(employee);
            }
            jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\attendance.json",dailyAttendanceList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return timeDate; // change return depending on the gui
    }

    /**
     * The generateDailyAttendance method will create a new attendance Daily Attendance object, containing all the
     * employees and their time in time out list. The new Daily Attendance object will be then stored in the attendance.json
     * file by utilizing teh JSONReaderWriter class and its methods. The codes that handle the creating and writing of
     * a new daily attendance object will only be executed if the current date is not within the attendance.json file.
     * @throws IOException IOException
     */
    public void generateDailyAttendance() throws IOException { // generate new attendance each day
        JSONReaderWriter<User> employeeReader = new JSONReaderWriter<>();
        processDateAndTime();
        List<DailyAttendance> attendanceList = readDailyAttendance(jsonReaderWriter);

        if (attendanceList==null || !Objects.equals(attendanceList.get(attendanceList.size() - 1).getDate(), date)) { // find if current date is in the attendance list, if not create a new attendance for that date
            Type userType = new TypeToken<List<User>>() {}.getType();

            List<User> employees = employeeReader.readJSONFile("src\\server_package\\" +
                    "json_files\\employeelist.json", userType); // read the employeelist.json and store all employees in a list

            List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>(); // list of employees and their initial attendance details
            List<String> attendance = new ArrayList<>(); // empty attendance time in time out for each employee

            if(employees!=null){
                for (User e : employees) { // populate the employeeAttendance list
                    employeeAttendanceList.add(new EmployeeAttendance(e.getEmployeeDetails().getEmployeeID(), "break", "0:0",
                            attendance, attendance));
                }
            }

            if(attendanceList==null){
                attendanceList = new ArrayList<>();
            }
            attendanceList.add(new DailyAttendance(date,"0:0",employeeAttendanceList)); // add the employee attendance list to the current attendance list

            jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\attendance.json",
                    attendanceList); // save the new attendance list in a j.son file
        }
    }

    /**
     * The employeeAttendance method reads the json file and extracts a specific employee's time in and time out record
     * for the current day.
     * @param id number of the employee
     * @return list of time in and time out of the specific employee
     * @throws IOException .
     */
    public List<List<String>> employeeAttendance(int id) throws IOException {
        // get currentDate
        processDateAndTime();
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter); // read the attendance.json and store it in a list
        List<List<String>>attendanceSummary = new ArrayList<>();

        // get index of json file with the current date
        int index = dailyAttendanceList.stream().map(DailyAttendance::getDate).toList().indexOf(date); // get index of object with current date

        // extract the current user's status
        if(dailyAttendanceList.get(index).getAttendanceList().stream().anyMatch
                (o -> id==o.getId())){ // find is user has already time in/time out
            int userIndex = dailyAttendanceList.get(index).getAttendanceList().stream().
                    map(EmployeeAttendance::getId).toList().indexOf(id); // get index of existing user
            EmployeeAttendance employee = dailyAttendanceList.get(index).getAttendanceList().get(userIndex);


            for(int i=0; i< employee.getTimeIn().size();i++){
                if(i<employee.getTimeOut().size()){
                    List<String> listTimeOut = new ArrayList<>();
                    listTimeOut.add("Time Out");
                    listTimeOut.add(employee.getTimeOut().get(i));
                    attendanceSummary.add(listTimeOut);
                }

                List<String> listTimeIn = new ArrayList<>();
                listTimeIn.add("Time In");
                listTimeIn.add(employee.getTimeIn().get(i));
                attendanceSummary.add(listTimeIn);
            }
        }
        Collections.reverse(attendanceSummary);
        return attendanceSummary;
    }

    /**
     * The getStatus method extracts the current status of an employee. The method will utilize the JSONReaderWriter class
     * and its method of retrieving the data of a json file. The program will then locate the index of the id number of
     * an employee in order to retrieve the employee's current work status.
     * @param id number of the employee
     * @return current status of an employee
     * @throws IOException IOException
     */
    public String getStatus(int id) throws IOException {
        // get currentDate
        processDateAndTime();
        Type type = new TypeToken<List<DailyAttendance>>() {}.getType();
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter);// read the attendance.json and store it in a list

        // get index of json file with the current date
        int index = dailyAttendanceList.stream().map(DailyAttendance::getDate).toList().indexOf(date); // get index of object with current date

        // extract the current user's status
        if(dailyAttendanceList.get(index).getAttendanceList().stream().anyMatch
                (o -> id==o.getId())){ // find is user has already time in/time out
            int userIndex = dailyAttendanceList.get(index).getAttendanceList().stream().
                    map(EmployeeAttendance::getId).toList().indexOf(id); // get index of existing user
            return dailyAttendanceList.get(index).getAttendanceList().get(userIndex).getStatus(); // get the status of the user
        }
        return "no status";
    }

    /**
     * The computeEmployeeTime computes the total hours worked of a specific employee by extracting the time regarding
     * time in and time out. Once the employee's number of worked hours has been computed by using the WorkTimeComputation
     * class, it will be then written in the json file.
     * @throws IOException .
     */
    public void computeEmployeeTime() throws IOException {
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter);// read the attendance.json and store it in a list
        if(dailyAttendanceList!=null){
            List<EmployeeAttendance> employeeAttendanceList = dailyAttendanceList.get(dailyAttendanceList.size()-1).getAttendanceList();
            if(dailyAttendanceList.get(dailyAttendanceList.size()-1).getTotalTimeRendered().equals("0:0") &&
                    employeeAttendanceList!=null){
                for(EmployeeAttendance e: employeeAttendanceList){
                    List<String>timeInList = e.getTimeIn();
                    List<String>timeOutList = e.getTimeOut();
                    WorkTimeComputation computeTimeDifference = new WorkTimeComputation();
                    for(int i=0; i<timeInList.size();i++){
                        computeTimeDifference.computeTimeDiff(timeInList.get(i),timeOutList.get(i));
                    }
                    e.setHoursWorked(computeTimeDifference.totalComputation());
                }
                jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\attendance.json",dailyAttendanceList);
            }
        }
    }

    /**
     * The computeTotalEmployeesTime method retrieves each employee's total worked hours for that specific day and add it together
     * by utilizing the WorkTimeComputation class. Once the total sum of all employee's worked hours has been computed, it
     * will be written in the json file.
     * @throws IOException .
     */
    public void computeTotalEmployeesTime() throws IOException {
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter);// read the attendance.json and store it in a list
        if(dailyAttendanceList!=null){
            List<EmployeeAttendance> employeeAttendanceList = dailyAttendanceList.get(dailyAttendanceList.size()-1).getAttendanceList();
            if(dailyAttendanceList.get(dailyAttendanceList.size()-1).getTotalTimeRendered().equals("0:0") &&
                    employeeAttendanceList!=null){
                WorkTimeComputation computeTotalTime = new WorkTimeComputation();
                for(EmployeeAttendance e: employeeAttendanceList){
                    computeTotalTime.setHourAndMinute(e.getHoursWorked());
                }
                dailyAttendanceList.get(dailyAttendanceList.size()-1).setTotalTimeRendered(computeTotalTime.totalComputation());
                jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\attendance.json",dailyAttendanceList);
            }
        }
    }

    /**
     * The getAttendanceDate method retrieves a list containing the dates of attendance and store it in a List.
     * @return String list of specified dates where there is attendance
     * @param startDate start of list
     * @param endDate end of list
     * @throws IOException .
     */
    //CONVERT THIS METHOD TO RETURN ARRAYLIST OF DATES. TO BE USED IN DISPLAYING LIST OF STORED DATES IN GENERATE REPORT LIST GUI. :>>
    public List<Date> getAttendanceDate(Date startDate, Date endDate) throws IOException, ParseException {
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter);// read the attendance.json and store it in a list
        List<Date> listOfDates = new ArrayList<>();
        if(dailyAttendanceList!=null){
            for (int i = 0; i < dailyAttendanceList.size(); i++) {
                DailyAttendance d = dailyAttendanceList.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date parseResult = sdf.parse(d.getDate());
                if (parseResult.equals(startDate) || parseResult.after(startDate))
                    if (parseResult.equals(endDate) || parseResult.before(endDate))
                        listOfDates.add(parseResult);
            }
        }


        return listOfDates;
    }

    /**
     * The generateEmployeeStatusList retrieves the data of all employees and their status for the current day. The data
     * will be then stored in a List of string lists
     * @return list of a list of strings which stores the name of the employee and their status
     * @throws IOException
     */
    public List<List<String>> generateEmployeeStatusList() throws IOException {
        List<DailyAttendance> dailyAttendanceList = readDailyAttendance(jsonReaderWriter);// read the attendance.json and store it in a list
        List<List<String>> employeeStatusList = new ArrayList<>();
        if(dailyAttendanceList!=null){
            List<EmployeeAttendance> employeeAttendanceList = dailyAttendanceList.get(dailyAttendanceList.size()-1).getAttendanceList();
            if(employeeAttendanceList!=null){
                for(EmployeeAttendance e : employeeAttendanceList){
                    List<String> employee = new ArrayList<>();
                    employee.add(e.getId()+"");
                    employee.add(e.getStatus());
                    employeeStatusList.add(employee);
                }
            }
        }else{
            List<String> employee = new ArrayList<>();
            employee.add(" ");
            employee.add(" ");
            employeeStatusList.add(employee);
        }
        return employeeStatusList;
    }
}
