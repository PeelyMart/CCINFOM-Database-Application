package Controller;

import DAO.OrderDB;
import DAO.OrderitemDAO;
import DAO.TableDAO;
import Model.OrderStatus;
import Model.Table;

public class TableActions {

    public static void closeTable(Table currTb, int customerID){
        int isPaid = PaymentControl.initiatePayment(OrderDB.getWholeOrderByTable(currTb.getTableId()), customerID);
        if(isPaid == 1) {
            currTb.setTableStatus(true);
        }
    }



    public static void initateTable(Table currTb, int staffInCharge){
        /*
            TODO:
            1. Access table
            2. Set as taken
            3. call openOrder
            4.
         */
        currTb.setTableStatus(false); //table is now taken
        OrderController.openOrder(currTb.getTableId(), staffInCharge);
        //TODO decide what to output if we need the actual openOrder
    }
}
