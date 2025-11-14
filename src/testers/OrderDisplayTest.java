package testers;

import DAO.OrderDB;
import Model.Order;
import Model.OrderItem;

public class OrderDisplayTest {

    public static void main(String[] args) {
        printOrder(OrderDB.getWholeOrder(1));
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
                            + ", Status: " + item.getStatusAsAtring());
            }
        } else {
            System.out.println("No items in this order.");
        }
    }
}
