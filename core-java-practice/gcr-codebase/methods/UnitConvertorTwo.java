package methods;

import java.util.*;
public class UnitConvertorTwo {
    public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Enter distance in km, miles, meters and feet ");
    	double distanceInKM = sc.nextDouble();
    	double distanceInMiles =sc.nextDouble();
    	double distanceInMeters =sc.nextDouble();
    	double distanceInFeet =sc.nextDouble();
    	double distanceInInches =sc.nextDouble();
    	
        System.out.println("km in miles: " + convertYardsToFeet(distanceInKM));
        System.out.println("miles in km: " + convertFeetToYards(distanceInMiles));
        System.out.println("meters in feet: " + convertMetersToInches(distanceInMeters));
        System.out.println("feet in meters: " + convertInchesToMeters(distanceInFeet));
        System.out.println("inches in centimeter: " + convertInchesToCentimeters(distanceInInches));
        sc.close();
    }
    
    //Method to convert yards to feet and return the value. Use following code to convert  double yards2feet = 3;
    public static double convertYardsToFeet(double yards) {
        double yards2feet = 3.0;
        return yards * yards2feet;
    }

    //Method to convert feet to yards and return the value. Use following code to convert  double feet2yards = 0.333333;
    public static double convertFeetToYards(double feet) {
        double feet2yards = 0.333333;
        return feet * feet2yards;
    }

    //Method to convert meters to inches and return the value. Use following code to convert  double meters2inches = 39.3701;
    public static double convertMetersToInches(double meters) {
        double meters2inches = 39.3701;
        return meters * meters2inches;
    }

    //Method to convert inches to meters and return the value. Use following code to convert  double inches2meters = 0.0254;
    public static double convertInchesToMeters(double inches) {
        double inches2meters = 0.0254;
        return inches * inches2meters;
    }

    //Method to convert inches to centimeters and return the value. Use the following code  double inches2cm = 2.54;
    public static double convertInchesToCentimeters(double inches) {
        double inches2cm = 2.54;
        return inches * inches2cm;
    }
}
