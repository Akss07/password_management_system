package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class ViewController {
	
	@FXML
    protected Button mainButton;

    @FXML
    protected TextField usernameTextField;

    @FXML
    protected PasswordField passwordField1;
    
    @FXML
    protected Label invalidLabel;

    public abstract void mainButtonOnAction();

    public void fieldOnEnter(KeyEvent event) {
        invalidLabel.setVisible(false);
        if (event.getCode() == KeyCode.ENTER) mainButtonOnAction();
    }

}
