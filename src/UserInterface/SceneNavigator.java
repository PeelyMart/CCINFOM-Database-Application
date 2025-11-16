package UserInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneNavigator {
    public static void switchScene(Button sourceButton, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            AnchorPane root = loader.load();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testClick(String buttonName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Test");
        alert.setHeaderText(null);
        alert.setContentText(buttonName + " clicked!");
        alert.showAndWait();
    }

    public static void showError(String msg) {
        showAlert(Alert.AlertType.ERROR, "Error", msg);
    }

    public static void showWarning(String msg) {
        showAlert(Alert.AlertType.WARNING, "Warning", msg);
    }

    public static void showInfo(String msg) {
        showAlert(Alert.AlertType.INFORMATION, "Info", msg);
    }

    public static void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void switchNoButton(Stage stage, String fxmlPath, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Get controller
            Object controller = loader.getController();

            // If controller has setData(), call it
            try {
                controller.getClass().getMethod("setData", Object.class).invoke(controller, data);
            } catch (NoSuchMethodException ignored) {
                // controller doesn't have setData → ignore
            }

            // Switch scene
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
