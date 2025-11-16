package Controller;

public class AuditReportTest {
    public static void main(String[] args) {
        AuditReportController ctrl = new AuditReportController();
        ctrl.printAuditReportForStaff(1);
        ctrl.printAuditReportForStaff(2);
        ctrl.printAuditReportForStaff(3);
        ctrl.printAuditReportForStaff(4);
    }
}
