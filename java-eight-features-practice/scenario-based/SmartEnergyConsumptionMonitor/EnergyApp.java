package SmartEnergyConsumptionMonitor;
import java.util.*;

public class EnergyApp {
    public static void main(String[] args) {
        SmartEnergyMonitor monitor = new SmartEnergyMonitor();

        Calendar cal = Calendar.getInstance();
        cal.set(2026, Calendar.FEBRUARY, 14);
        Date day1 = cal.getTime();

        cal.set(2026, Calendar.FEBRUARY, 15);
        Date day2 = cal.getTime();

        try {
            monitor.addReading(day1, 12.5);
            monitor.addReading(day1, 10.0);
            monitor.addReading(day2, 15.0);
            monitor.addReading(day2, -5.0); //it is invalid
        } catch (InvalidEnergyReadingException e) {
            System.out.println(e.getMessage());
        }

        monitor.showReport(Calendar.FEBRUARY, 2026);
    }
}