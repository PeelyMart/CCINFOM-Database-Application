package Controller;

import DAO.MenuItemDAO;
import DAO.OrderitemDAO;
import Model.MenuItem;

import java.lang.invoke.MethodHandle;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuPerformanceController {
    private final MenuItemDAO MenuItemDAO = new MenuItemDAO();

    /** 
    * returns the performance (qty sold and total sales) of all menu items in a date range
    * Key: MenuItem, Value: { total quantity sold, total sales } 
    */

   public Map<MenuItem, double[]> getMenuPerformance(LocalDate start, LocalDate end) {
        Map<Integer, double[]> raw = OrderitemDAO.MenuSales(start, end);
        Map<MenuItem, double[]> perf = new LinkedHashMap<>();
        for (Map.Entry<Integer, double[]> entry : raw.entrySet()) {
            MenuItem item = MenuItemDAO.getMenuItemById(entry.getKey());
            
            if (item != null) {
                perf.put(item, entry.getValue());
            }
        }
        return perf;
   }

    public String generateMenuPerformanceReport(LocalDate start, LocalDate end) {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu Performance Report for ").append(start).append(" to ").append(end).append(":\n");
        Map<MenuItem, double[]> report = getMenuPerformance(start, end);

        if (!report.isEmpty()) {
            // Determine the max length of menu names
            int maxNameLength = "Item".length();
            for (MenuItem item : report.keySet()) {
                maxNameLength = Math.max(maxNameLength, item.getMenuName().length());
            }

            // Header
            sb.append(String.format("%-" + maxNameLength + "s | %12s | %12s\n", "Item", "Quantity Sold", "Total Sales"));
            sb.append("-".repeat(maxNameLength + 27)).append("\n"); // separator line

            // Data rows
            for (Map.Entry<MenuItem, double[]> entry : report.entrySet()) {
                MenuItem item = entry.getKey();
                double[] result = entry.getValue();
                sb.append(String.format("%-" + maxNameLength + "s | %12.0f | %12.2f\n",
                        item.getMenuName(), result[0], result[1]));
            }
        } else {
            sb.append("Empty set\n");
        }

        return sb.toString();
    }
}