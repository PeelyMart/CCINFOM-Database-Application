package UserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/MainMenu/dashboard.fxml"));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("dashboard");
        stage.setScene(scene);
        stage.show();
    }

         */

        // Load the login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Login/login.fxml"));
        AnchorPane loginRoot = loader.load();

        Scene loginScene = new Scene(loginRoot);

        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setResizable(false);
        loginStage.setScene(loginScene);

// Get controller and set stages
        LoginUI UI = loader.getController();
        UI.setStages(loginStage, stage);  // 'stage' is your main stage

        loginStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
