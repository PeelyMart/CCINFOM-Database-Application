package Controller;

import DAO.OrderDB;
import Model.Order;
import Model.OrderItem;

import java.math.BigDecimal;

public class OrderController {



    public static void openOrder(int tableId, int staffId) {
        OrderDB.newOrder(tableId, staffId);
    };

    public static void updateTotal(Order currOrd){
        if(!currOrd.getOrderItems().isEmpty()){
            BigDecimal totalOrder = BigDecimal.valueOf(0);
            for(OrderItem item : currOrd.getOrderItems()){
                if(item.getStatus()) {
                    totalOrder = totalOrder.add(item.getSubtotal());
                }
            }
            currOrd.setTotalCost(totalOrder);
        }
        else{
            currOrd.setTotalCost(BigDecimal.valueOf(0.00));
        }

    }





    private static void updateLinks(Order orderHeader){
        //TODO: find the OrderHeader id, ask a query to database
        //TODO: find order_items and link it and add to the array of the order header
        //TODO: update running total




    }







}
