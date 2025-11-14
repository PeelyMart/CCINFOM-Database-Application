package UserInterface;

import Controller.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboardUI {

    @FXML
    private BorderPane mainContent;



    @FXML
    private Label cashierLabel;

    @FXML
    private void handleOptions(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/options.fxml");
    }

    @FXML
    private void handleReports(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/reports.fxml");
    }

    @FXML
    private void handleTransactions(ActionEvent event) throws IOException {
        loadContent("/Resources/MainMenu/transactions.fxml"); // or your logout screen
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException{
        UserService.logOut();
    }

    /**
     * Utility to load FXML into the center of mainContent
     *
     */
    private void loadContent(String fxmlFile) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
        mainContent.setCenter(view);
    }

    @FXML
    public void setCashierName(String name) {
        cashierLabel.setText("Cashier: " + name);
    }
}
