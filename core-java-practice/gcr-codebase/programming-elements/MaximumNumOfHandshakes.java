// a program to find the maximum number of handshakes among N number of students.
import java.util.Scanner;
public class MaximumNumOfHandshakes {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        int numberOfStudents = sc.nextInt();

        // Use the combination = (n * (n - 1)) / 2 formula to 
        // calculate the maximum number of possible handshakes.
        int maxNumberOfHandshakes = (numberOfStudents * (numberOfStudents - 1)) / 2;

        System.out.println("The number of possible handshakes is "+ maxNumberOfHandshakes);
        sc.close();
    }
}
