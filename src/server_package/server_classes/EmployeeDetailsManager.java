package server_package.server_classes;

import com.google.gson.reflect.TypeToken;
import common_package.Exceptions.InvalidCredentialsException;
import common_package.objects.User;
import common_package.objects.EmployeeDetails;
import server_package.tools.Encryption;
import server_package.tools.JSONReaderWriter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailsManager {
    private final int employeeID;
    private final String employeeContactNo;
    private final String employeeEmergencyContact;
    private final String employeeName;
    private final String employeePosition;
    private final String employeeBirthday;
    private final String employeeAddress;
    private final String employeeSex;
    private final String employeeDepartment;
    private final String username;
    private String password;

    private static List<User> registrationList = new ArrayList<>();
    private static List<User> employeeList = new ArrayList<>();

    /**
     * Constructor for the EmployeeDetailsManager class.
     * @param employeeID is the Employee's ID number.
     * @param employeeContactNo is the Employee's Contact Number.
     * @param employeeEmergencyContact is the Employee's Emergency Contact Number.
     * @param employeeName is the Employee's Name.
     * @param employeePosition is the Employee's Position.
     * @param employeeBirthday is the Employee's Birthday.
     * @param employeeAddress is the Employee's Address.
     * @param employeeSex is the Employee's Sex.
     * @param employeeDepartment is the Employee's Department.
     * @param username is the Employee's Username.
     * @param password is the Employee's Password.
     */
    public EmployeeDetailsManager(int employeeID, String employeeContactNo, String employeeEmergencyContact,
                                  String employeeName, String employeePosition, String employeeBirthday,
                                  String employeeAddress, String employeeSex, String employeeDepartment,
                                  String username, String password){
        this.employeeID = employeeID;
        this.employeeContactNo = employeeContactNo;
        this.employeeEmergencyContact = employeeEmergencyContact;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.employeeBirthday = employeeBirthday;
        this.employeeAddress = employeeAddress;
        this.employeeSex = employeeSex;
        this.employeeDepartment = employeeDepartment;
        this.username = username;
        this.password = password;
    }

    /**
     * Reads the registrationList.json and stores the data on an ArrayList.
     * @return the List of Registrations
     */
    public static List<User> populateRegistrationList(){
        Type type = new TypeToken<List<User>>(){}.getType();
        JSONReaderWriter<User> jsonReaderWriter = new JSONReaderWriter<>();
        registrationList = new ArrayList<>();

        try{
            registrationList = jsonReaderWriter
                    .readJSONFile("src\\server_package\\json_files\\registrationList.json", type);
            if(registrationList==null){
                return new ArrayList<>();
            }
        }catch (IOException exception){
            return registrationList;
        }
        return registrationList;
    }

    /**
     * Reads the employeeList.json and stores the data on an ArrayList
     * @return the List of Employees
     */
    public static List<User> populateEmployeeList(){
        Type type = new TypeToken<List<User>>(){}.getType();
        JSONReaderWriter<User> jsonReaderWriter = new JSONReaderWriter<>();
        employeeList = new ArrayList<>();
        try{
            employeeList = jsonReaderWriter
                    .readJSONFile("src\\server_package\\json_files\\employeeList.json", type);
            if(employeeList==null){
                return new ArrayList<>();
            }
        } catch (IOException exception){
            return employeeList;
        }
        return employeeList;
    }

    /**
     * Removes a specific User from the registrationList and
     * rewrites the contents of the registrationList.json
     */

    public void deleteRegistration(){
        JSONReaderWriter<User> jsonReaderWriter = new JSONReaderWriter<>();
        try{
            int userIndex = registrationList.stream().map(User::getUsername).toList().indexOf(username);
            registrationList.remove(userIndex);
            jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\registrationList.json", registrationList);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * This method creates a User object to be stored in either the registrationList or the employeeList
     * depending on the condition. The object will be stored for both the ArrayList and the JSON file.
     *
     * @param condition indicates if the program executed is for registration or employee.
     * @return Returns a String pertaining to the error found when checking the credentials.
     * @throws InvalidCredentialsException when the given credentials does not match to any account in the JSON file.
     */
    public String createEmployee(String condition) throws InvalidCredentialsException {
        JSONReaderWriter<User> jsonReaderWriter = new JSONReaderWriter<>();
        switch(condition){
            case "registration" -> {
                try{
                    SecretKey secretKey = Encryption.generateKeyFromPassword(password);
                    password = Encryption.convertSecretKeyToString(secretKey);

                    registrationList = populateRegistrationList();
                    employeeList = populateEmployeeList();
                    if(registrationList.size()!=0){
                        if(registrationList.stream().anyMatch(o -> employeeID == o.getEmployeeDetails().getEmployeeID())
                                || employeeList.stream().anyMatch(o -> employeeID == o.getEmployeeDetails().getEmployeeID())){
                            return "Employee ID already exists.";
                        } else if(registrationList.stream().anyMatch(o -> username.equalsIgnoreCase(o.getUsername()))
                                || employeeList.stream().anyMatch(o -> username.equalsIgnoreCase(o.getUsername()))){
                            return "Username already exists";
                        } else {
                            EmployeeDetails employeeDetails = new EmployeeDetails(employeeID, employeeContactNo, employeeEmergencyContact, employeeName,
                                    employeePosition, employeeBirthday, employeeAddress, employeeSex, employeeDepartment);
                            registrationList.add(new User(employeeDetails, username, password));
                        }
                    }else{
                        registrationList = new ArrayList<>();
                        EmployeeDetails employeeDetails = new EmployeeDetails(employeeID, employeeContactNo, employeeEmergencyContact, employeeName,
                                employeePosition, employeeBirthday, employeeAddress, employeeSex, employeeDepartment);
                        registrationList.add(new User(employeeDetails, username, password));

                    }
                    jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\registrationList.json", registrationList);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            case "employee" -> {
                try{
                    employeeList = populateEmployeeList();
                    EmployeeDetails employeeDetails = new EmployeeDetails(employeeID, employeeContactNo, employeeEmergencyContact, employeeName,
                            employeePosition, employeeBirthday, employeeAddress, employeeSex, employeeDepartment);
                    User user = new User(employeeDetails, username, password);
                    if(employeeList==null){
                        employeeList=new ArrayList<>();
                    }
                    employeeList.add(user);
                    AttendanceManager attendanceManager = new AttendanceManager();
                    attendanceManager.processTimeInTimeOut(employeeID,"break","0:0");
                    jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\employeeList.json", employeeList);

                    deleteRegistration();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
