package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.PasswordModel;
import utils.PasswordHashing;
import utils.UserAppDetail;


public class UserAllPasswordPage extends HBox {
	private UserAppDetail userAppDetail;
	private PasswordModel model;
	
	@FXML
    private Button editButton;
	
	@FXML
	protected Label appNameLabel;

    public void initialize(UserAppDetail userAppDetail) { 
    	this.userAppDetail = userAppDetail; 
    	appNameLabel.setText(userAppDetail.getDomain());
    }
    
    public void initializeModel(PasswordModel model) { this.model = model; }

    public void copyUsernameButtonOnAction() { copy(userAppDetail.getUserName()); }

    public void copyPasswordButtonOnAction() {
    	try {
    		
    		PasswordHashing passwordHashing = new PasswordHashing();
    		
    		System.out.println(userAppDetail.getPassword());
    		
        	String password = passwordHashing.decrypt(userAppDetail.getPassword());
        	System.out.println(password);
        	
        	copy(password); 
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	}

    private void copy(String string) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(string), null);
    }

    public void buttonOnEnter() {
        
    }

    public void buttonOnExit() {
        
    }
    
    // New method to handle the edit button action
    public void editButtonAction() {
    	System.out.println("Edit button clicked");
        openEditPasswordPage();
    }
    
    private void openEditPasswordPage() {
        try {
        	String viewPath = PasswordModel.VIEW_DIRECTORY + "EditPasswordPageView.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent parent = loader.load();
            EditPasswordPage editController = loader.getController();
            editController.initialize(userAppDetail);
            editController.initializeModel(model);
            
            // Create a new stage for the edit password page
            Stage editStage = new Stage();
            editStage.setTitle("Edit Password");
            editStage.setScene(new Scene(parent));
            editStage.setResizable(false);
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
