package UserInterface;

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
       LoginUI.openLogin(stage);
    }



    public static void main(String[] args) {
        launch();
    }
}
