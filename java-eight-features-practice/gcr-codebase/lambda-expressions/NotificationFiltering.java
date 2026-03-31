import java.util.*;
import java.util.function.Predicate;
class Alert {
    String type;
    String message;

    Alert(String type, String message) {
        this.type =type;
        this.message=message;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }
}

public class NotificationFiltering {
    public static void main(String[] args) {
        List<Alert> alerts = Arrays.asList(
                new Alert("EMERGENCY", "Heart rate critical"),
                new Alert("APPOINTMENT", "Doctor visit at 10 AM"),
                new Alert("MEDICATION", "Take insulin"),
                new Alert("GENERAL", "Hospital closed on Sunday")
        );

        //only Emergency & medication alerts according user
        Predicate<Alert> emergencyFilter =
                alert -> alert.type.equals("EMERGENCY");

        Predicate<Alert> medicationFilter =
                alert -> alert.type.equals("MEDICATION");

        Predicate<Alert> userPreference =
                emergencyFilter.or(medicationFilter);

        System.out.println(" Alerts shown to user- ");
        alerts.stream().filter(userPreference).forEach(System.out::println);
    }
}
