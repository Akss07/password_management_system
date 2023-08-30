package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.UserAccount;
import utils.UserAppDetail;
import utils.UserDetail;

public class PasswordModel {

    private Map<String, UserDetail> userMap;
    private UserDetail currentUser; // Logged In user
    private String usersDirectory;

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final String MAIN_DIRECTORY = ".." + File.separator;
    public static final String SRC_DIRECTORY = "src" + File.separator;
    public static final String VIEW_DIRECTORY = MAIN_DIRECTORY + "view" + File.separator;
    public static final String DATA_DIRECTORY = SRC_DIRECTORY + "dataFile" + File.separator;

    public PasswordModel() {
        userMap = new HashMap<String, UserDetail>();
        // The following does not work for some reason:
        // DATA_DIRECTORY + x"users.txt"
        usersDirectory = DATA_DIRECTORY + "users.txt";
        try {
            Scanner scanner = new Scanner(new File(usersDirectory));
            String username;
            String password;
            while (scanner.hasNextLine()) {
                username = scanner.next();
                password = scanner.next();
                userMap.put(username, new UserDetail(new UserAccount(username, password)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   
    public void setUser(UserDetail userDetail) {
        this.currentUser = userDetail;
    }

    
    public UserDetail getCurrentUser() {
        return currentUser;
    }

    
    public String getCurrentUserName() {
        return currentUser.getAccount().getUserName();
    }

  
    public UserDetail getUser(String username) {
        return userMap.get(username);
    }

   
    public boolean hasUser(String username) {
        return userMap.containsKey(username);
    }

  
    public boolean isCorrectPassword(String username, String enteredPassword) {
        if (!hasUser(username))
            return false;
        return getUser(username).getAccount().getPassword().equals(enteredPassword);
    }

   
    public void addUser(String username, String password) {
        try { // add user to database, i.e. "users.txt"
            File usersFile = new File(usersDirectory);
            FileWriter writer = new FileWriter(usersFile, true);
            writer.write("\n" + username + " " + password);
            writer.close();

            // create username.txt file
            File userFile = new File(DATA_DIRECTORY + username + ".txt");
            userFile.createNewFile();

            // add user to userMap
            userMap.put(username, new UserDetail(new UserAccount(username, password)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    
    public void updatePassword(UserAppDetail userAppDetail, String userNameLogin) {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");  
        Date date = new Date();
        String currentDate = formatter.format(date); 

        String username = userAppDetail.getUserName();
        String domain = userAppDetail.getDomain();
        String newPassword = userAppDetail.getPassword();
        
        System.out.println("Insavve" + userNameLogin);
        // Update the password in the model or storage
        UserDetail userDetail = userMap.get(userNameLogin);
        
        if (userDetail != null) {
            UserAppDetail updatedAppDetail = userDetail.getInternetAccount(domain);
            if (updatedAppDetail != null) {
                System.out.println("userName" + username);
                UserAppDetail newAppDetail = new UserAppDetail(domain, username, newPassword, currentDate);
                userDetail.addInternetAccount(newAppDetail);
                showPasswordUpdateAlert(domain);
                
            }
        }
        
    }
    
    private void showPasswordUpdateAlert(String domain) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Password Updated");
        alert.setHeaderText(null);
        alert.setContentText("Password for domain '" + domain + "' has been successfully updated.");
        alert.showAndWait();
    }
	
}
