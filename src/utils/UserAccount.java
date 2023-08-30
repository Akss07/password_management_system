package utils;

public class UserAccount {
	
	private String username;
    private String password;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return this.username;
    }
   
    public String getPassword() {
        return this.password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

}
