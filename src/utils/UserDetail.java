package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.PasswordModel;

public class UserDetail {
	private UserAccount userAccount;
    private HashMap<String, UserAppDetail> userAppDetails;

    // take account as parameter because maybe we want to add more info to account in future
    public UserDetail(UserAccount account) {
        this.userAccount = account;
        this.userAppDetails = new HashMap<String, UserAppDetail>();

        try {
            Scanner scanner = new Scanner(new File(PasswordModel.DATA_DIRECTORY + account.getUserName() +
                    ".txt"));
            String domain;
            String username;
            String password;
            String currentDate;
            while (scanner.hasNextLine()) {
                domain = scanner.next();
                username = scanner.next();
                password = scanner.next();
                currentDate = scanner.next();
                userAppDetails.put(domain, new UserAppDetail(domain, username, password, currentDate));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }	
	

    public UserAppDetail getInternetAccount(String domain) { return userAppDetails.get(domain); }

    
    public UserAccount getAccount() { return userAccount; }

   
    public HashMap<String, UserAppDetail> getInternetAccounts() { return userAppDetails; }

   
    public void addInternetAccount(UserAppDetail userAppDetail) {
        try {
            String domain = userAppDetail.getDomain();
            String username = userAppDetail.getUserName();
            String password = userAppDetail.getPassword();
            String currentDate = userAppDetail.getCurrentDate();
            File file = new File(PasswordModel.DATA_DIRECTORY + userAccount.getUserName() + ".txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write("\n" + domain + " " + username + " " + password + " " + currentDate);
            writer.close();
            userAppDetails.put(domain, new UserAppDetail(domain, username, password, currentDate));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
