package Controller;

import DAO.PaymentDAO;
import Model.Payment;
import Model.PaymentReport;

import java.time.LocalDateTime;
import java.util.*;

public class SalesReportController {

   public static void getTransactionReport(List<PaymentReport> list){
       if(!list.isEmpty()){
           for(PaymentReport row: list){
               System.out.println(row.getType() + "\n" + row.getTransactions() + "\n" + row.getTotal());
           }
       }else{
            System.out.print("list is empty");
       }

   }

    public static String getTransactionReportAsString(List<PaymentReport> list) {
        StringBuilder sb = new StringBuilder();

        if (!list.isEmpty()) {
            for (PaymentReport row : list) {
                sb.append("Payment Method: ").append(row.getType()).append("\n")
                        .append("Transactions: ").append(row.getTransactions()).append("\n")
                        .append("Total Processed: ").append(row.getTotal()).append("\n")
                        .append("------------------------------------\n");
            }
        } else {
            sb.append("No transaction data available.\n");
        }

        return sb.toString();
    }

    // Example helpers to get start/end for day/month/year
    public static LocalDateTime getDayStart(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 0, 0);
    }

    public static LocalDateTime getDayEnd(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 23, 59, 59);
    }

    public static LocalDateTime getMonthStart(int year, int month) {
        return LocalDateTime.of(year, month, 1, 0, 0);
    }

    public static LocalDateTime getMonthEnd(int year, int month) {
        int lastDay = java.time.YearMonth.of(year, month).lengthOfMonth();
        return LocalDateTime.of(year, month, lastDay, 23, 59, 59);
    }

    public static LocalDateTime getYearStart(int year) {
        return LocalDateTime.of(year, 1, 1, 0, 0);
    }

    public static LocalDateTime getYearEnd(int year) {
        return LocalDateTime.of(year, 12, 31, 23, 59, 59);
    }
}