package UserInterface;

import Controller.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardUI {

    private Stage mainStage;

    @FXML
    private BorderPane mainContent;

    // ------------------------------
    // Static method to open dashboard
    // ------------------------------
    public static void openDashboard(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(DashboardUI.class.getResource("/Resources/MainMenu/dashboard.fxml"));
            Parent root = loader.load();

            DashboardUI controller = loader.getController();
            controller.setMainStage(mainStage); // safely assign stage

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setTitle("Dashboard");
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------
    // Assign mainStage safely
    // ------------------------------
    public void setMainStage(Stage stage) {
        this.mainStage = stage;
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
        // Optionally navigate to login after logout:
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
