package UserInterface;

import Controller.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginUI {

    @FXML
    private TextField staffIDField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private Stage loginStage;  // popup
    private Stage mainStage;   // main application stage

    public static void openLogin(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginUI.class.getResource("/Resources/Login/login.fxml"));
            AnchorPane root = loader.load();

            LoginUI controller = loader.getController();
            controller.mainStage = mainStage;

            Stage loginStage = new Stage();
            controller.loginStage = loginStage;

            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.APPLICATION_MODAL); // modal popup
            loginStage.setResizable(false);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
    }

    public void setStages(Stage loginStage, Stage mainStage) {
        this.loginStage = loginStage;
        this.mainStage = mainStage;
    }

    @FXML
    private void handleLogin() {
        int resultFlag = UserService.logIn(
                stringHelper(staffIDField.getText()),
                stringHelper(passwordField.getText())
        );

        if (resultFlag == 0) {
            // Success: close login popup
            if (loginStage != null) loginStage.close();

            // Open dashboard in main stage
            DashboardUI.openDashboard(mainStage);
        } else {
            System.out.println("Login failed or input error");
        }
    }

    private int stringHelper(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
