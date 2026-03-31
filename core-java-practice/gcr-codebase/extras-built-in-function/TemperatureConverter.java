import java.util.*;
public class TemperatureConverter {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("1. Fahrenheit to Celsius");
        System.out.println("2. Celsius to Fahrenheit");
        System.out.print("It is a temperature converter, Choose conversion 1 or 2: ");
        int choosen = scanner.nextInt();

        if (choosen==1) {
            System.out.print("Enter temperature in Fahrenheit - ");
            double fahrenheit = scanner.nextDouble();
            System.out.println("Conversion from fahrenheit to celsius is - "+fahrenheitToCelsius(fahrenheit));
        } else if (choosen==2) {
            System.out.print("Enter temperature in Celsius - ");
            double celsius = scanner.nextDouble();
            System.out.println("Conversion from celsius to fahrenheit is - "+celsiusToFahrenheit(celsius));
        }
    }
	
	//this function is to convert Fahrenheit to Celsius
    static double fahrenheitToCelsius(double fahren) {
        return (fahren-32) * 5/9;
    }

    //this function is to convert Celsius to Fahrenheit
    static double celsiusToFahrenheit(double cel) {
        return (cel*9/5) + 32;
    }
}
