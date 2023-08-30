package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PasswordModel;

public class MainLauncher extends Application {
	
    @Override
    public void start(Stage loginStage) throws Exception {
        String loginDirectory = PasswordModel.VIEW_DIRECTORY + "LoginPageView.fxml";
        FXMLLoader loader = new FXMLLoader((getClass().getResource(loginDirectory)));
        Parent parent = loader.load();
        loginStage.setScene(new Scene(parent));
        loginStage.setResizable(false);
        loginStage.show();
    }
    
}
