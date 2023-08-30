package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PasswordModel;
import utils.PasswordHashing;
import utils.UserAppDetail;

public class AddPasswordPage extends ViewController {
	
    @FXML
    private TextField domainTextField;

    @FXML
    private PasswordField passwordField2;

    private MainPage mainPage;

    public void initialize(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    @Override
    public void mainButtonOnAction() {
        String domain = domainTextField.getText();
        String username = usernameTextField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");  
        Date date = new Date();
        String currentDate = formatter.format(date);
        System.out.println(currentDate); 

        if (!password1.equals(password2)) {
            invalidLabel.setText("Passwords do not match");
            invalidLabel.setVisible(true);
        } else {
        	PasswordHashing passwordHashing = new PasswordHashing();
        	try {
        		String hashedPassword = passwordHashing.hashPassword(password1);
        		UserAppDetail newAppDetail = new UserAppDetail(domain, username, hashedPassword, currentDate);
        		mainPage.userDetail.addInternetAccount(newAppDetail);
        		mainPage.borderPane.setDisable(false);
        		mainPage.addPasswordHBox(newAppDetail);
        		mainPage.addPassStage.close();
        	}catch(Exception ex) {
        		ex.printStackTrace();
        	}
        }
    }
    
    public void genratePassword() {
    	System.out.println("Generate Password ");
    	
    	try {
            String viewPath = PasswordModel.VIEW_DIRECTORY + "GeneratePasswordPage.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Stage passwordStage = new Stage();
            Parent parent = loader.load();
            GeneratePasswordPage generatePasswordPage = loader.getController();
            generatePasswordPage.initialize(mainPage);
            passwordStage.initStyle(StageStyle.UTILITY);
            passwordStage.setTitle("Generated Password");
            passwordStage.setScene(new Scene(parent));
            passwordStage.setResizable(false);
            passwordStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
