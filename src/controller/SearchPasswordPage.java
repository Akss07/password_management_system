package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.PasswordHashing;
import utils.UserAppDetail;
import model.PasswordModel;

public class SearchPasswordPage extends ViewController {
	
    @FXML
    private TextField searchdomainTextField;

    private MainPage mainPage;

    public void initialize(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    @Override
    public void mainButtonOnAction() {
    	System.out.println("Inside controller Search Password");
        String domain = searchdomainTextField.getText();
        System.out.println("domain = " + domain);  
        
        UserAppDetail userAppDetail = findUserAppDetailByDomain(domain);
        if (userAppDetail != null) {
            // Load and display the detail view page
            try {
                String viewPath = PasswordModel.VIEW_DIRECTORY + "ShowSearchedDetailsViewPage.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
                Stage detailStage = new Stage();
                Parent parent = loader.load();
                ShowSearchedDetailsViewPage showSearchedDetailsViewPage = loader.getController();
                showSearchedDetailsViewPage.initialize(userAppDetail);
                detailStage.setTitle("Detail View");
                detailStage.setScene(new Scene(parent));
                detailStage.setResizable(false);
                detailStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            
            invalidLabel.setText("No details found for the domain: " + domain);
            invalidLabel.setVisible(true);
        }
     }
    
    private UserAppDetail findUserAppDetailByDomain(String domain) {
    	System.out.println("Find User Name... ");
    	try {
        for (UserAppDetail appDetail : mainPage.userDetail.getInternetAccounts().values()) {
            if (appDetail.getDomain().equalsIgnoreCase(domain)) {
            	System.out.println(appDetail.getUserName());
            	PasswordHashing passwordHashing = new PasswordHashing();
            	String password = passwordHashing.decrypt(appDetail.getPassword());
            	UserAppDetail uad = new UserAppDetail(appDetail.getDomain(), appDetail.getUserName(), password);
                return uad;
            }
        }
        // No matching detail found
    }
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return null; 
}
}