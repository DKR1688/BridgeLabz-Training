// a program to find the distance in yards and miles for the distance provided by the user in feet
import java.util.Scanner;
public class DistanceInYardsAndMiles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int distanceInFeet = scanner.nextInt();

        // 1 mile = 1760 yards and 1 yard is 3 feet
        double distanceInYards = distanceInFeet / 3.0;
        double distanceInMiles = distanceInYards / 1760.0;

        System.out.println("The distance in yards is "+distanceInYards+ " while the distance in miles is "+distanceInMiles);
        scanner.close();
    }
    
}
