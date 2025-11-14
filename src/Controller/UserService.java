package Controller;

import DAO.StaffDB;
import Model.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class UserService {
    private static Staff current_user = null ;
    private static boolean isLoggedIn = false;
    @FXML private TextField staffIDField;
    /**
        Login method,
        @return 1 = incorrect username/user cannot be found, 2 = incorrect password, 0= login, 999 - input error
    */
    public static int logIn(int userID, int userPass){
        if(userID == -1 || userPass == -1){
            return  999;
        }
        Staff retrieved = StaffDB.findById(userID);
        if(retrieved != null){
            if(retrieved.getStaffPin() == userPass) {
                current_user = retrieved;
                return 0;
            }
        }
        else{
            return 1 ;
        }
        return 2;
    }

    public static void logOut(){
        current_user = null;
        isLoggedIn = false;
    }


    //TODO: if the system detects an exit withoput logout find a way to safely logout for proper session keeping
    public static Staff getCurrentUser(){
        return current_user;
    }



}
