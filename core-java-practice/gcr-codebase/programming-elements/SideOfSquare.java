// a program to find the side of the square whose parameter you read from the user 
import java.util.Scanner;
public class SideOfSquare {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int perimeter=scanner.nextInt();

        // Perimeter of the Square is 4 times the side
        int side=perimeter/4;

        System.out.println("The side of square is "+side+" whose perimeter is "+perimeter);
        scanner.close();
    }
    
}
