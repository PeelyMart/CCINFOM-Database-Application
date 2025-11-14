package testers;

import DAO.OrderDB;
import Model.Order;
import Model.OrderItem;

public class orderTest {
    public static void main(String[] args) {
        int orderId = OrderDB.newOrder(2, 2);
        if (orderId != -1){
            System.out.println("Your order_header is successfully uploaded: " +  orderId);

        }
        else{
            System.out.println("Something went wrong check db");
        }
        printOrder(OrderDB.getWholeOrder(2));

    }


    public static void printOrder(Order order) {
        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Table ID: " + order.getTableId());
        System.out.println("Staff ID: " + order.getStaffId());
        System.out.println("Order Time: " + order.getOrderTime());
        System.out.println("Total Cost: " + order.getTotalCost());
        System.out.println("Status: " + order.getStatus());

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            System.out.println("\nOrder Items:");
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("Item ID: " + item.getOrderItemId()
                        + ", Menu ID: " + item.getMenuId()
                        + ", Quantity: " + item.getQuantity()
                        + ", Subtotal: " + item.getSubtotal()
                        + ", Status: " + item.getStatus());
            }
        } else {
            System.out.println("No items in this order.");
        }
    }
}

