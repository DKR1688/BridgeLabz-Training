// a program to find the sum until the user enters 0 or a negative number using while loop and break statement

import java.util.Scanner;
public class SumOfNumbersUntillNegative {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a variable total of type double initialize to 0.0. 
        double total = 0.0;

        // using infinite while loop to sum numbers until user enters 0 or negative number
        while (true) {
            double userEnters= scanner.nextDouble();

            // here we are checking if user entered 0 or negative number to break the loop
            if (userEnters<=0) {
                break;
            }

            total+=userEnters; 
        }
        // here displaying the sum value
        System.out.println("The total sum is: "+total);
        scanner.close();
    }
}