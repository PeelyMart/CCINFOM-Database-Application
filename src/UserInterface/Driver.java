package UserInterface;

import Controller.NavController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavController.setStage(stage);
        //setting the nav
        NavController.registerScreens(); //populate a directory of fxml paths
        //Start with login screen
        NavController.navigate("login");
    }



    public static void main(String[] args) {
        launch(args);
    }
}
