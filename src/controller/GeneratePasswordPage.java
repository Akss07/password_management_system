package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import utils.PasswordGenerator;

public class GeneratePasswordPage extends ViewController {
    @FXML
    private Label generatedPasswordLabel;

    private MainPage mainPage;

    public void initialize(MainPage mainPage) {
        this.mainPage = mainPage;
        generatePassword();
    }

    @FXML
    public void generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String generatedPassword = passwordGenerator.generateStrongPassword(12); // You can adjust the length as needed
        generatedPasswordLabel.setText(generatedPassword);
    }

    @FXML
    public void copyPassword() {
        String password = generatedPasswordLabel.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(password);
        clipboard.setContent(content);
    }

	@Override
	public void mainButtonOnAction() {
		// TODO Auto-generated method stub
		
	}
	
	public void buttonOnEnter() {
        
    }

    public void buttonOnExit() {
        
    }
}
