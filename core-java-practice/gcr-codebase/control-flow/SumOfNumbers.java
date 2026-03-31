// a program to find the sum of numbers until the user enters 0

import java.util.Scanner;
public class SumOfNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a variable total of type double initialize to 0.0. 
        // Also, create a variable to store the double value the user enters
        double total = 0.0;
        double userEnters= scanner.nextDouble();

        // using while loop to sum numbers until user enters 0
        while (userEnters!=0) {
            total += userEnters; 
             
            // here asking user to input again
            userEnters = scanner.nextDouble();
        }
        // here displaying the sum value
        System.out.println("The total sum is: "+total);
        scanner.close();
    }
}
