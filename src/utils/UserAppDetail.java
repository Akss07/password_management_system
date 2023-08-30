package utils;

public class UserAppDetail extends UserAccount{

	private String domain;
	
	private String currentDate;

    public UserAppDetail(String domain, String username, String password) {
        super(username, password);
        this.domain = domain;
    }
    
    public UserAppDetail(String domain, String username, String password, String currentDate) {
    	super(username, password);
    	this.domain = domain;
    	this.currentDate = currentDate;
    }

    public String getDomain() {
        return domain;
    }

    public void changeDomain(String domain) { 
    	this.domain = domain; 
    	}
    
    public String getCurrentDate() {
		return currentDate;
	}

}
