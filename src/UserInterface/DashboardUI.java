package UserInterface;

import Controller.UserService;
import Model.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardUI {

    @FXML
    private TextField cashierField;

    @FXML
    private TextField timeInField;

    @FXML
    private BorderPane mainContent;

    @FXML
    private TextArea printGreeting;

    private Stage mainStage;

    // ------------------------------
    // Open dashboard
    // ------------------------------
    public static void openDashboard(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(DashboardUI.class.getResource("/Resources/MainMenu/dashboard.fxml"));
            Parent root = loader.load();

            DashboardUI controller = loader.getController();
            controller.setMainStage(mainStage);

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setTitle("Dashboard");
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------
    // Assign mainStage
    // ------------------------------
    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    // ------------------------------
    // Initialize UI: cashier name + time-in
    // ------------------------------
    @FXML
    private void initialize() {
        Staff currentUser = UserService.getCurrentUser();
        if (currentUser != null) {
            cashierField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            printGreeting.setText("Welcome, " + currentUser.getFirstName() + "!");
        } else {
            printGreeting.setText("Welcome!");
        }

        // Auto-set current time
        timeInField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    // ------------------------------
    // Menu button handlers
    // ------------------------------
    @FXML
    private void handleOptions(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/options.fxml");
    }

    @FXML
    private void handleReports(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/reports.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        UserService.logOut();
        // Optionally go back to login:
        // LoginUI.openLogin(mainStage);
    }

    @FXML
    private void handleTransactions(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/transactions.fxml");
    }

    // ------------------------------
    // Utility: load FXML into mainContent
    // ------------------------------
    private void loadContent(String fxmlFile) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
        mainContent.setCenter(view);
    }
}
