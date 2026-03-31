package methods;

import java.util.*;
public class UnitConvertorOne {
    public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Enter distance in km, miles, meters and feet ");
    	double distanceInKM = sc.nextDouble();
    	double distanceInMiles =sc.nextDouble();
    	double distanceInMeters =sc.nextDouble();
    	double distanceInFeet =sc.nextDouble();
    	
        System.out.println("km in miles: " + convertKmToMiles(distanceInKM));
        System.out.println("miles in km: " + convertMilesToKm(distanceInMiles));
        System.out.println("meters in feet: " + convertMetersToFeet(distanceInMeters));
        System.out.println("feet in meters: " + convertFeetToMeters(distanceInFeet));
        sc.close();
    }
    
	//Method To convert kilometers to miles and return the value. Use the following code  double km2miles = 0.621371;
    public static double convertKmToMiles(double km) {
        double km2miles = 0.621371;
        return km * km2miles;
    }

    //Method to convert miles to kilometers and return the value. Use the following code  double miles2km = 1.60934;
    public static double convertMilesToKm(double miles) {
        double miles2km = 1.60934;
        return miles * miles2km;
    }
    
  //Method to convert feet to meters and return the value. Use the following code to convert  double feet2meters = 0.3048;
    public static double convertFeetToMeters(double feet) {
        double feet2meters = 0.3048;
        return feet * feet2meters;
    }

    //Method to convert meters to feet and return the value. Use the following code to convert  double meters2feet = 3.28084;
    public static double convertMetersToFeet(double meters) {
        double meters2feet = 3.28084;
        return meters * meters2feet;
    }
}
