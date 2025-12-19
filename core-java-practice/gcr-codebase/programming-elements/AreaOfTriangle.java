//  a program that takes the base and height in cm to 
// find the area of a triangle in square inches and square centimeters 
import java.util.Scanner;
public class AreaOfTriangle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int base = scanner.nextInt();
        int height = scanner.nextInt();

        // Area of a Triangle is ½ * base * height and 1 in = 2.54 cm
        double areaInCm = 0.5 * base * height;
        double areaInInches = areaInCm / (2.54*2.54);

        System.out.println("The Area of the trigle in sq in is "+areaInInches+" and in sq cm is "+areaInCm);
        scanner.close();
    }
}
