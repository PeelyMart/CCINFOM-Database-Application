package UserInterface;

import Controller.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    @FXML
    private Text statusText; // optional

    private Stage loginStage;  // popup stage
    private Stage mainStage;   // main application stage


    public static void openLogin(Stage mainStage){
        try {
            FXMLLoader loader = new FXMLLoader(LoginUI.class.getResource("/Resources/Login/login.fxml"));
            AnchorPane root = loader.load();

            LoginUI controller = loader.getController();
            controller.mainStage = mainStage;

            Stage loginStage = new Stage();
            controller.loginStage = loginStage;

            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setResizable(false);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int stringHelper(String input){
        try{
            return Integer.parseInt(input);
        }catch (NumberFormatException e){
            return -1;
        }
    }

    // Method to set stage references
    public void setStages(Stage loginStage, Stage mainStage) {
        this.loginStage = loginStage;
        this.mainStage = mainStage;
        initialize();
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
    }

    @FXML
    private void handleLogin() {
        String username = staffIDField.getText().trim();
        String password = passwordField.getText().trim();
        int resultFlag = UserService.logIn(stringHelper(username), stringHelper(password));
        switch(resultFlag){
            case 0:
                System.out.println("Logged in as: " + UserService.getCurrentUser().getFirstName());
                break;
            case 1:
            case 2:
                System.out.println("Credential Error");
                break;
            case 999:
                System.out.println("Input Error");
            default:
        }
    }
}
