
public class DataExportFeature {
	public static void main(String[] args) {
        ReportExporter exporter =new SalesReportExporter();

        exporter.exportToCSV("Sales data");
        exporter.exportToPDF("Sales data");
        exporter.exportToJSON("Sales data");
    }
}

interface ReportExporter {
    void exportToCSV(String data);
    void exportToPDF(String data);

    //adding default method for JSON
    default void exportToJSON(String data) {
        System.out.println("Exporting data to JSON- { \"report\": \"" + data + "\" }");
    }
}

class SalesReportExporter implements ReportExporter {
    @Override
    public void exportToCSV(String data) {
        System.out.println("Exporting sales report to CSV- "+data);
    }

    @Override
    public void exportToPDF(String data) {
        System.out.println("Exporting sales report to PDF- "+data);
    }
}
