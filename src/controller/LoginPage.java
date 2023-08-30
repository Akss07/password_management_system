package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.PasswordModel;

public class LoginPage extends ViewController{
	
	    @FXML
	    private Button registerButton;

	    PasswordModel model;
	    Stage stage;

	    public LoginPage() {
	        model = new PasswordModel();
	        System.out.println("Login Page");
	    }

	   
	    @Override
	    public void mainButtonOnAction() {
	        String username = usernameTextField.getText();
	        String password = passwordField1.getText();
	        if (model.hasUser(username) && model.isCorrectPassword(username, password)) {
	            model.setUser(model.getUser(username));
	            System.out.println("Logged in: " + model.getCurrentUserName());
	            openMainWindow();
	        } else {
	            invalidLabel.setVisible(true);
	            invalidLabel.setText("Invalid login. Please try again.");
	        }
	    }
	    
	    /**
	     * Opens the main password manager window.
	     */
	    private void openMainWindow() {
	        try {
	            String viewPath = PasswordModel.VIEW_DIRECTORY + "MainPage.fxml";
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
	            Stage mainStage = new Stage();
	            Parent parent = loader.load();
	            MainPage mainPage = loader.getController();
	            mainPage.initialize(model);
	            mainStage.setTitle("Password Manager");
	            mainStage.setScene(new Scene(parent));
	            mainStage.setResizable(false);
	            mainStage.show();
	            mainButton.getScene().getWindow().hide();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	    /**
	     * Opens register window.
	     */
	    public void registerButtonAction() {
	        System.out.println("Registering new user");
	        try {
	            String viewPath = PasswordModel.VIEW_DIRECTORY + "RegisterPageView.fxml";
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
	            stage = new Stage();
	            Parent parent = loader.load();
	            RegisterPage register = loader.getController();
	            register.initialize(this);
	            stage.setTitle("Register");
	            stage.setScene(new Scene(parent));
	            stage.setResizable(false);
	            stage.show();
	            //            registerButton.getScene().getWindow().hide();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	  
	 

}
