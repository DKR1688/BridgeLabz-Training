// a program that takes your height in centimeters and converts it into feet and inches
import java.util.Scanner;

public class HeightInCentimetersConverted {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double heightInCM = scanner.nextDouble();

        // here we are converting centimeters to inches and feet
        double heightInInches = heightInCM / 2.54;
        double heightInFeet = heightInInches / 12;

        System.out.println("Your height in cm is "+heightInCM+" while in feet is "+heightInFeet+" and inches is "+heightInInches);
        scanner.close();
    }
}
