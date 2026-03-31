import java.util.function.Predicate;

public class TemperatureAlertSystem {
    public static void main(String[] args) {

        double currentTemperature = 42.5;
        double threshold = 40.0;

        //predicate to check if temperature crosses threshold
        Predicate<Double> temperatureAlert = temp -> temp > threshold;

        if (temperatureAlert.test(currentTemperature))System.out.println("Alert! Temperature crossed the threshold.");
        else System.out.println("Temperature is normal.");
        
    }
}
