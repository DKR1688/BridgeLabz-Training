// Maya’s BMI Fitness Tracker 🏃‍♀️
//Maya, a fitness coach, wants to record the BMI of her clients.
//  Ask for height and weight.
//  Use formula BMI = weight / (height * height)
//  Print category using if-else: Underweight, Normal, Overweight.
// Use clear variable names and comments to maintain code hygiene.

import java.util.Scanner;

public class BmiTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter height and weight of clients ");
        double height =scanner.nextDouble();
        double weight =scanner.nextDouble();

        //here we are calculating BMI using formula BMI = weight / (height * height)
        double bmi = weight/(height*height);

        //here we are printing category of bmi
        if (bmi<18.4) {
            System.out.println("Underweight");
        } else if (bmi>=18.5 && bmi<=24.9) {
            System.out.println("Normal");
        } else {
            System.out.println("Overweight");
        }
        scanner.close();
    }
}