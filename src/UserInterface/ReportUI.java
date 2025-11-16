package UserInterface;

import Controller.AuditReportController;
import Controller.LoyaltyReportController;
import Controller.MenuPerformanceController;
import Controller.SalesReportController;
import DAO.PaymentDAO;
import DAO.StaffDB;
import Model.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportUI {

    @FXML
    private TextArea reportTextArea;

    @FXML
    private Button staffButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button loyaltyButton;

    @FXML
    private Button salesButton;

    @FXML
    private ComboBox<String> staffDropdown;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    private String activeReport = ""; // "sales" or "loyalty"



    @FXML
    public void initialize() {


        List<Staff> staffList = StaffDB.returnAllStaff();
        for (Staff s : staffList) {
            staffDropdown.getItems().add(s.getStaffId() + " - " + s.getFirstName());
        }

        staffDropdown.setOnAction(e -> {
            String selected = staffDropdown.getValue();
            if (selected == null) return;

            int staffId = Integer.parseInt(selected.split(" - ")[0]);

            String report = AuditReportController.generateAuditReportForStaff(staffId);
            reportTextArea.setText(report);
        });


        staffButton.setOnAction(e -> {
            fromDatePicker.setVisible(false);
            toDatePicker.setVisible(false);
            staffDropdown.setVisible(true);
            reportTextArea.clear();
            loadStaffReport();
        });


        menuButton.setOnAction(e -> {
            activeReport = "menu";
            staffDropdown.setVisible(false);
            fromDatePicker.setVisible(true);
            toDatePicker.setVisible(true);
            staffDropdown.setVisible(false);
            reportTextArea.setText("MENU REPORT...");
        });

        loyaltyButton.setOnAction(e -> {
            activeReport = "loyalty";
            staffDropdown.setVisible(false);
            fromDatePicker.setVisible(true);
            toDatePicker.setVisible(true);
            reportTextArea.clear();
        });

        salesButton.setOnAction(e -> {
            activeReport = "sales";
            staffDropdown.setVisible(false);
            fromDatePicker.setVisible(true);
            toDatePicker.setVisible(true);
            reportTextArea.clear();
            loadSalesReport();
        });
        //<---trigger an update when two values are added-->//
        fromDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateReport());
        toDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateReport());
    }

    private void updateReport() {
        if (fromDatePicker.getValue() == null || toDatePicker.getValue() == null) return;

        switch (activeReport) {
            case "sales":
                loadSalesReport();
                break;
            case "loyalty":
                loadLoyaltyReport(fromDatePicker.getValue(), toDatePicker.getValue());
                break;
            case "menu":
                loadMenuReport(fromDatePicker.getValue(), toDatePicker.getValue());
                break;
        }
    }

    private void loadStaffReport() {

        StringBuilder allReports = new StringBuilder();
        for (int i = 0; i < StaffDB.findAmountOfStaff(); i++) {
            String report = AuditReportController.generateAuditReportForStaff(i);
            allReports.append(report).append("\n\n"); // add spacing between reports
        }
        reportTextArea.setText(allReports.toString());

    }

    private void loadMenuReport(LocalDate from, LocalDate to) {
        MenuPerformanceController controller= new MenuPerformanceController();
        reportTextArea.setText(controller.generateMenuPerformanceReport(from, to));
    }

    private void loadLoyaltyReport(LocalDate startMonth, LocalDate endMonth) {
        LoyaltyReportController controller = new LoyaltyReportController();
        Map<LocalDate, Integer> monthlyNewMembers = controller.getNewMembersByMonth(startMonth, endMonth);
        reportTextArea.setText(LoyaltyReportController.generateMonthlyMembersReport(monthlyNewMembers));
    }

    private void loadSalesReport() {
        reportTextArea.setText(SalesReportController.getTransactionReportAsString(PaymentDAO.getPaymentReport()));
    }
}
