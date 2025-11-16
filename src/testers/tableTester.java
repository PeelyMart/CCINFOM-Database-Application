package testers;

import DAO.TableDAO;
import Model.Table;
import java.util.List;
public class tableTester {
    public static void main(String[] args) {
        List<Table> result = TableDAO.getAllTables();
        for(Table cur : result){
            System.out.println(cur.getTableId() + ", " + cur.getCapacity() + ", " + cur.getTableStatus());
        }
    }
}
