package Controller;

import DAO.MenuItemDAO;
import DAO.OrderitemDAO;
import Model.MenuItem;
import java.time.LocalDate;
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
}