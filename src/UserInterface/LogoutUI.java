package UserInterface;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogoutUI {

    @FXML
    private Button reloginButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextArea logoutTimeField;

    @FXML
    private void initialize() {
        // Display logout time
        LocalDateTime logoutTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Logged out at: HH:mm:ss on MMM dd, yyyy");
        logoutTimeField.setText(logoutTime.format(formatter));

        // Relogin button - go back to login screen
        reloginButton.setOnAction(e -> {
            Stage stage = (Stage) reloginButton.getScene().getWindow();
            SceneNavigator.switchScene(reloginButton, "/Resources/LogIn/login.fxml");
        });

        // Exit button - close the application
        exitButton.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
    }
}

