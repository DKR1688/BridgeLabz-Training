package methods;

import java.util.*;
public class UnitConvertorThree {
    public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Enter quantity in fahrenheit, celsius, pounds, kilograms, gallons and liters ");
    	double fahrenheit = sc.nextDouble();
    	double celsius =sc.nextDouble();
    	double pounds =sc.nextDouble();
    	double kilograms =sc.nextDouble();
    	double gallons =sc.nextDouble();
    	double liters =sc.nextDouble();
    	
        System.out.println("Fahrenheit in celsius: " + convertFahrenheitToCelsius(fahrenheit));
        System.out.println("Celsius in fehrenheit: " + convertCelsiusToFahrenheit(celsius));
        System.out.println("Pounds in kilograms: " + convertPoundsToKilograms(pounds));
        System.out.println("Kilograms in pounds: " + convertKilogramsToPounds(kilograms));
        System.out.println("Gallons in litersr: " + convertGallonsToLiters(gallons));
        System.out.println("Liters in gallons: " + convertLitersToGallons(liters));
        sc.close();
    }
    
    //Method to convert Fahrenheit to Celsius and return the value. Use the following code  double farhenheit2celsius = (farhenheit - 32) * 5 / 9;
    public static double convertFahrenheitToCelsius(double fahrenheit) {
        double fahrenheit2celsius = (fahrenheit - 32) * 5.0 / 9.0;
        return fahrenheit2celsius;
    }

    //Method to convert Celsius to Fahrenheit and return the value. Use the following code  double celsius2farhenheit = (celsius * 9 / 5) + 32;
    public static double convertCelsiusToFahrenheit(double celsius) {
        double celsius2fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
        return celsius2fahrenheit;
    }

    //Method to convert pounds to kilograms and return the value. Use the following code  double pounds2kilograms = 0.453592;
    public static double convertPoundsToKilograms(double pounds) {
        double pounds2kilograms = 0.453592;
        return pounds * pounds2kilograms;
    }

    //Method to convert kilograms to pounds and return the value. Use the following code  double kilograms2pounds = 2.20462;
    public static double convertKilogramsToPounds(double kilograms) {
        double kilograms2pounds = 2.20462;
        return kilograms * kilograms2pounds;
    }

    //Method to convert gallons to liters and return the value. Use following code to convert   double gallons2liters = 3.78541;
    public static double convertGallonsToLiters(double gallons) {
        double gallons2liters = 3.78541;
        return gallons * gallons2liters;
    }

    //Method to convert liters to gallons and return the value. Use following code to convert  double liters2gallons = 0.264172;
    public static double convertLitersToGallons(double liters) {
        double liters2gallons = 0.264172;
        return liters * liters2gallons;
    }
}
