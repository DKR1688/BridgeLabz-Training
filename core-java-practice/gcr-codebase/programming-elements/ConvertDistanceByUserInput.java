import java.util.Scanner;
public class ConvertDistanceByUserInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter distance in kilometers: ");
        double km = scanner.nextDouble();

        // here we are converting kilometers to miles
        double miles = km * 0.621371;
        System.out.println("Distance in miles: " + miles);
        scanner.close();
    }
}
