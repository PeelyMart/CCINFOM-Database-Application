package UserInterface;

import Controller.Nav;
import UserInterface.LoginUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set main stage in Nav
        Nav.setStage(primaryStage);

        // Open login as a modal popup
        LoginUI.openLogin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
