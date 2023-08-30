package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.PasswordModel;

public class RegisterPage extends ViewController {

    @FXML
    private PasswordField passwordField2;

    private LoginPage login;

    public void initialize(LoginPage loginPage) {
        this.login = loginPage;
    }


    @Override
    public void mainButtonOnAction() {
        String username = usernameTextField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        if (login.model.hasUser(username)) {
            invalidLabel.setText("User exists");
            invalidLabel.setVisible(true);
        } else if (!password1.equals(password2)) {
            invalidLabel.setText("Passwords do not match");
            invalidLabel.setVisible(true);
        } else if (password1.length() < PasswordModel.MIN_PASSWORD_LENGTH) {
            invalidLabel.setText("Min password length: " + PasswordModel.MIN_PASSWORD_LENGTH);
            invalidLabel.setVisible(true);
        } else {
            login.model.addUser(username, password1);
            login.stage.close();
        }
    }
    
    @FXML
    public void passwordStrengthAnalysis() {
        String password1 = passwordField1.getText();
        int passwordLength = password1.length();
        if (passwordLength <= 5) {
        	invalidLabel.setText("Password Strength: Weak");
        } else if (passwordLength <= 10) {
        	invalidLabel.setText("Password Strength: Strong");
        } else {
        	invalidLabel.setText("Password Strength: Very Strong");
        }
    }
    
    @FXML
    public void fieldOnEnter() {
        invalidLabel.setVisible(false);
    }
}
