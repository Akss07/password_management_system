package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.PasswordModel;
import utils.PasswordHashing;
import utils.UserAppDetail;

public class EditPasswordPage {
	
    @FXML
    private PasswordField oldPasswordField;
    
    @FXML
    private PasswordField newPasswordField;
    
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button updateButton;
    
    @FXML
    private Label unMatchedPassword;
    
    private UserAppDetail userAppDetail;

    private PasswordModel passwordModel;


    public EditPasswordPage() {
    }
    
    public void initializeModel(PasswordModel model) { this.passwordModel = model; }
    
    public void initialize(UserAppDetail userAppDetail) {
        this.userAppDetail = userAppDetail;
        System.out.println("userAppDetail" + userAppDetail.getUserName());
        // Initialize input fields with current password details
        oldPasswordField.setText(userAppDetail.getPassword());
    }
       

    @FXML
    private void saveButtonAction() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        if (!newPassword.equals(confirmPassword)) {
            // Handle password mismatch
        	unMatchedPassword.setText("Passwords do not match");
        	unMatchedPassword.setVisible(true);
            return;
        }
        
        try {
        	PasswordHashing passwordHashing = new PasswordHashing();
        	String hashedPassword = passwordHashing.hashPassword(newPassword);
        	// Update the password
            userAppDetail.changePassword(hashedPassword);
        }catch(Exception ex) {
        	ex.printStackTrace();
        }

        // Update the data in the model or storage
        passwordModel.updatePassword(userAppDetail, this.passwordModel.getCurrentUserName());
        
        // Close the edit password stage
        ((Stage) newPasswordField.getScene().getWindow()).close();
    }


}
