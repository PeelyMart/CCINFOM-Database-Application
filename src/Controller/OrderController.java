package Controller;

import DAO.OrderDB;
import Model.Order;

public class OrderController {



    public static void openOrder(int tableId, int staffId) {
        OrderDB.newOrder(tableId, staffId);
    };

    public static void updateTotal(int table ){}





    private static void updateLinks(Order orderHeader){
        //TODO: find the OrderHeader id, ask a query to database
        //TODO: find order_items and link it and add to the array of the order header
        //TODO: update running total




    }







}
