package methods;

import java.util.*;
public class WindChillTemperature {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the temperature and speed of wind ");
        double temperature =scanner.nextDouble();
        double windSpeed =scanner.nextDouble();
        
        double ans =calculateWindChill(temperature, windSpeed);
        System.out.println("The wind chill temprature is "+ans);
        scanner.close();
    }
	
	//a method to calculate the wind chill temperature using the formula 
	//windChill = 35.74 + 0.6215 *temp + (0.4275*temp - 35.75) * windSpeed^0.16
	public static double calculateWindChill(double temperature, double windSpeed) {
	      return 35.74 + 0.6215 * temperature +
	               (0.4275 * temperature - 35.75) * Math.pow(windSpeed, 0.16);
	    }
}
