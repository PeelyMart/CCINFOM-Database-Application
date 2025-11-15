package Controller;

import UserInterface.DashboardUI;
import UserInterface.LoginUI;
import javafx.stage.Stage;

public class Nav {

    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getStage() {
        return mainStage;
    }

    public static void navigate(String screenName) {
        if (mainStage == null) {
            System.out.println("Nav mainStage not set!");
            return;
        }

        switch (screenName.toLowerCase()) {
            case "login":
                LoginUI.openLogin(mainStage);
                break;
            case "dashboard":
                DashboardUI.openDashboard(mainStage);
                break;
            default:
                System.out.println("Screen not found: " + screenName);
        }
    }
}
