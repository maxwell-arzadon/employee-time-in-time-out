package server_package.server_classes;

import com.google.gson.reflect.TypeToken;
import server_package.Admin;
import server_package.tools.Encryption;
import server_package.tools.JSONReaderWriter;

import javax.crypto.SecretKey;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
    private final String username;
    private String password;
    private static List<Admin> admin = new ArrayList<>();

    /**
     * Constructor for the AdminManager class;
     * @param username is the username entered by the Admin.
     * @param password is the password entered by the Admin.
     */
    public AdminManager(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * This method edits the admins credentials in the JSON file. The edited password
     * will be encrypted.
     */
    public void editAdminCredentials(){
        JSONReaderWriter<Admin> jsonReaderWriter = new JSONReaderWriter<>();
        try{
            SecretKey secretKey = Encryption.generateKeyFromPassword(password);
            password = Encryption.convertSecretKeyToString(secretKey);
            admin = new ArrayList<>();
            admin.add(new Admin(username, password));
            jsonReaderWriter.writeToJSON("src\\server_package\\json_files\\admin.json", admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks if the entered admin credentials matches
     * the credentials inside the JSON file.
     * @return true if the credentials matches the data in the JSON file. Otherwise, it returns false.
     */
    public boolean validateAdmin(){
        Type type = new TypeToken<List<Admin>>(){}.getType();
        JSONReaderWriter<Admin> jsonReaderWriter = new JSONReaderWriter<>();
        admin = new ArrayList<>();
        try{
            SecretKey secretKey = Encryption.generateKeyFromPassword(password);
            password = Encryption.convertSecretKeyToString(secretKey);
            admin = jsonReaderWriter.readJSONFile("src\\server_package\\json_files\\admin.json", type);
            String uname = admin.get(0).getAdminUsername();
            String pass = admin.get(0).getAdminPassword();

            return uname.equalsIgnoreCase(username) && pass.equals(password);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }
}
