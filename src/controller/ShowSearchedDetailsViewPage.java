package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.UserAppDetail;

public class ShowSearchedDetailsViewPage extends ViewController{
	
	@FXML
    private Label domainLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;
    
    private String password;

    public void initialize(UserAppDetail userAppDetail) {
        domainLabel.setText("Domain: " + userAppDetail.getDomain());
        usernameLabel.setText("Username: " + userAppDetail.getUserName());
        this.password = userAppDetail.getPassword();
//        passwordLabel.setText("Password: " + userAppDetail.getPassword());
    }
    
    public void buttonOnEnter() {
        
    }

    public void buttonOnExit() {
        
    }
    
    @Override
	public void mainButtonOnAction() {
		copy(password);
		
	}
    
    private void copy(String string) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(string), null);
    }
}

