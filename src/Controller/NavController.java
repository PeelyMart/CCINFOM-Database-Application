package Controller;

import UserInterface.dashboardUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


public class NavController {
    private static Stage stage;
    private static HashMap<String, String> screens = new HashMap<>(); //this is a more complex data type basically linking two data under one unit...

    /**
     * This
     */
    public static void registerScreens(){
        addScreen("login", "/Resources/Login/login.fxml");
        addScreen("dashboard", "/Resources/MainMenu/dashboard.fxml");
    }

    public static void setStage (Stage s){
        stage = s;
    }

    public static void addScreen(String name, String fxml){
        screens.put(name, fxml);
    }

    public static void navigate(String screenName) {
        try {
            String fxml = screens.get(screenName);
            if(fxml == null){
                System.out.println("[Missing]: " + screenName);
                return;
            }


            // Use class reference instead of getClass()
            FXMLLoader loader = new FXMLLoader(NavController.class.getResource(fxml));
            Parent root = loader.load();  // <-- loads FXML and calls initialize()

            if(screenName.equals("dashboard")) {
                dashboardUI controller = loader.getController();  // get controller instance
                controller.setCashierName(UserService.getCurrentUser().getFirstName());           // update your label
            }

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
