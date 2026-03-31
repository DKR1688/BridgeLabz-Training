// a program to convert distance in kilometers to miles.
import java.util.Scanner;
public class ConvertDistanceWithScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // taking user input for distance in kilometers
        System.out.print("Enter distance in kilometers: ");
        double km = scanner.nextDouble();

        // here we are converting kilometers to miles
        double miles = km * 0.621371;
        System.out.println("Distance in miles: " + miles);
        scanner.close();
    }
}
