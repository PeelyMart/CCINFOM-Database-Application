package Controller;

import Controller.MenuPerformanceController;
import Model.MenuItem;
import java.time.LocalDate;
import java.util.Map;

public class MenuPerformanceTest {
    public static void main(String[] args) {
        
        // change to desired date range
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        MenuPerformanceController controller = new MenuPerformanceController();
        Map<MenuItem, double[]> report = controller.getMenuPerformance(start, end);

        System.out.println("Menu Performance Report for " + start + " to " + end + ":");
        if(!report.isEmpty()) {
            for (Map.Entry<MenuItem, double[]> entry : report.entrySet()) {
                MenuItem item = entry.getKey();
                double[] result = entry.getValue();
                System.out.printf("Item: %-20s | Quantity Sold: %5.0f | Total Sales: %8.2f\n",
                        item.getMenuName(), result[0], result[1]);
            }
        }
        else{
            System.out.println("Empty set");
        }
    }
}
