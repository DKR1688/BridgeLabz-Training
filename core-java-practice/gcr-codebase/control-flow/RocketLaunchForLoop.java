// a program to count down the number from the user input value to 1 using a for loop for a rocket launch

import java.util.Scanner;
public class RocketLaunchForLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // taking user input for countdown start value
        int counter = scanner.nextInt();

        // if the counter is greater than or equal to 1, continue the countdown
        // here we are using for loop instead of while loop
        for (int i=counter; i>=1; i--) {
            System.out.println(i);
        }
        scanner.close();
    }

}