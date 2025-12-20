// a program to find the BMI of a person

import java.util.*;
public class BmiOfPerson {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);

        // Taking user input for weight in kg and height in cm
        System.out.print("Enter weight in kg and height in cm: ");
        double weight = scanner.nextDouble();
        double height = scanner.nextDouble();

        // the formula BMI = weight / (height * height). Note unit is kg/m^2.
        double heightInMeter = height / 100;
        double bmi = weight / (heightInMeter*heightInMeter);
        System.out.println("The BMI is " + bmi);

        // Determining weight status based on BMI
        if (bmi<=18.4) {
            System.out.println("Underweight");
        } else if (bmi>=18.5 && bmi<24.9) {
            System.out.println("Normal");
        } else if (bmi>=25.0 && bmi<39.9) {
            System.out.println("Overweight");
        } else {
            System.out.println("Obese");
        }
        scanner.close();
    }
}