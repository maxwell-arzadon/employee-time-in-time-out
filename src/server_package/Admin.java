package server_package;

public class Admin {
    private String adminUsername;
    private String adminPassword;

    public Admin(String adminUsername, String adminPassword){
        this.setAdminUsername(adminUsername);
        this.setAdminPassword(adminPassword);
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
