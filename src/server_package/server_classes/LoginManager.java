package server_package.server_classes;

import common_package.objects.User;
import server_package.tools.Encryption;

import javax.crypto.SecretKey;
import java.util.List;

public class LoginManager {
    private final String username;
    private String password;

    /**
     * Constructor for the LoginManager class
     * @param username is the username entered by the user.
     * @param password is the password entered by the user.
     */
    public LoginManager(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * This method verifies if the entered credentials exists in the JSON file.
     * @return the User object found in the JSON file.
     */
    public User verifyCredentials(){
        try{
            int userIndex;
            SecretKey secretKey = Encryption.generateKeyFromPassword(password);
            password = Encryption.convertSecretKeyToString(secretKey);

            List<User> employeeList = EmployeeDetailsManager.populateEmployeeList();
            if(employeeList.stream().anyMatch(o -> username.equalsIgnoreCase(o.getUsername()))){
                userIndex = employeeList.stream().map(User::getUsername).toList().indexOf(username);
                if(!employeeList.get(userIndex).getPassword().equals(password)){
                    return null;
                }
            } else {
                return null;
            }
            return employeeList.get(userIndex);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("and4");
            return null;
        }
    }
}
