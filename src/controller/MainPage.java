package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import launcher.MainLauncher;
import model.PasswordModel;
import utils.UserAppDetail;
import utils.UserDetail;



public class MainPage {
    Stage addPassStage;
    UserDetail userDetail;

    @FXML
    Button addNewPasswordButton;

    @FXML
    VBox passwordsVBox;

    @FXML
    private Button logoutButton;

    @FXML
    BorderPane borderPane;
    
    PasswordModel model;

    public void initialize(PasswordModel model) {
    	this.model = model;
        System.out.println("Model transferred from login window to main window");
        System.out.println("This account has these domains stored:");

        this.userDetail = model.getCurrentUser();
        for (UserAppDetail userAppDetail : userDetail.getInternetAccounts().values()) {
            addPasswordHBox(userAppDetail);
        }
        
        ArrayList<String> expiredDomains = checkPasswordExpiration();
        if (!expiredDomains.isEmpty()) {
            System.out.println("Alert");
            showExpirationAlert(expiredDomains);
        }
    }

    /**
     * Opens the window for adding new passwords.
     */
    public void addNewPasswordButtonAction() {
        System.out.println("Adding new password...");
        try {
            String viewPath = PasswordModel.VIEW_DIRECTORY + "AddPassPageView.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            addPassStage = new Stage();
            Parent parent = loader.load();
            AddPasswordPage addPassPage = loader.getController();
            addPassPage.initialize(this);
            addPassStage.setTitle("Add Password");
            addPassStage.setScene(new Scene(parent));
            addPassStage.setResizable(false);
            addPassStage.show();
            borderPane.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    public void logoutButtonAction() throws Exception {
        logoutButton.getScene().getWindow().hide();
        MainLauncher mainLauncher = new MainLauncher();
        mainLauncher.start(new Stage());
    }

    public void searchButtonAction() throws Exception{
    	System.out.println("Search Password Page.....");
    	try {
            String viewPath = PasswordModel.VIEW_DIRECTORY + "SearchPassViewPage.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Stage searchStage = new Stage();
            Parent parent = loader.load();
            SearchPasswordPage searchPasswordPage = loader.getController();
            searchPasswordPage.initialize(this);
            searchStage.setTitle("Search Password");
            searchStage.setScene(new Scene(parent));
            searchStage.setResizable(false);
            searchStage.show();
            borderPane.setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> checkPasswordExpiration() {
    	System.out.println("Check Password Expiration");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
        Date currentDate = new Date();
        ArrayList<String> domainlist = new ArrayList<>();

        for (UserAppDetail appDetail : this.userDetail.getInternetAccounts().values()) {
        	System.out.println(appDetail.getCurrentDate());
            try {
                Date storedDate = formatter.parse(appDetail.getCurrentDate());
                System.out.println("storedDate " + storedDate);
                long diffInMillies = currentDate.getTime() - storedDate.getTime();
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInDays > 90) { // More than 3 months
                	domainlist.add(appDetail.getDomain());
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return domainlist;
    }
    
    private void showExpirationAlert(ArrayList<String> domains) {
		System.out.println("In Alert");
		// Load the expiration alert pop-up window
		 StringBuilder message = new StringBuilder();
		 message.append("Passwords for the following domains haven't been changed in 3 months:\n\n");
		 for (String domain : domains) {
		        message.append("- ").append(domain).append("\n");
		    }
		// Additional message about updating passwords
		 message.append("\nPlease update them for better security.");
		 message.append("\nRemember, your secure password is your first line of defense!");
		 
		 Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Password Expiration Alert");
		 alert.setHeaderText("Passwords Need Update");
		 alert.setContentText(message.toString());
		 
		// Set a custom dialog size
		    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		    stage.setWidth(400); // Adjust the width as needed
		    stage.setHeight(400); // Adjust the height as needed
		
      
      alert.showAndWait();
}
   
    public void addPasswordHBox(UserAppDetail userAppDetail) {
        try {
            String viewDir = PasswordModel.VIEW_DIRECTORY + "UserAllPassPageView.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewDir));
            Parent hbox = loader.load();
            //HBox hbox = loader.load();
            UserAllPasswordPage userPasswordPage = loader.getController();
            userPasswordPage.initialize(userAppDetail);
            userPasswordPage.initializeModel(model);
            passwordsVBox.getChildren().add(hbox);
            //appNameLabel.setText(userAppDetail.getDomain());
//            HBox subHBox = (HBox) hbox.getChildren().get(1);
//            ((Button) (subHBox.getChildren().get(0))).setText(userAppDetail.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
