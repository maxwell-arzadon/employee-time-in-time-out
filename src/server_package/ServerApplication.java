package server_package;


import server_package.ServerGUI.LogInGUIS;
import server_package.server_classes.AttendanceManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerApplication {

    static Registry registry;
    public ServerApplication() {
    }

    public static void main(String[] args) {
        ServerApplication server = new ServerApplication();
        new LogInGUIS(server);
    }


    public void startServer(){
        try{
            Servant servant = new Servant();
            registry = LocateRegistry.createRegistry(1900);
            registry.rebind("TimeInTimeOutServer", servant);
            try {
                createDailyAttendance();
                computeHoursRendered();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws NotBoundException, RemoteException {
        registry.unbind("TimeInTimeOutServer");
    }

    private static void createDailyAttendance() throws IOException {
        AttendanceManager attendanceManager = new AttendanceManager();
        attendanceManager.generateDailyAttendance();
    }

    private static void computeHoursRendered() throws IOException {
        AttendanceManager computeHoursRendered = new AttendanceManager();
        computeHoursRendered.computeEmployeeTime();
        computeHoursRendered.computeTotalEmployeesTime();
    }
}
