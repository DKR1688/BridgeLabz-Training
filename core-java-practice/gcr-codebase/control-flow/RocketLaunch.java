// a program to count down the number from the user input value to 1 using a while loop for a rocket launch

import java.util.Scanner;
public class RocketLaunch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // taking user input for countdown start value
        int counter = scanner.nextInt();

        // if the counter is greater than or equal to 1, continue the countdown
        while (counter>=1) {
            System.out.println(counter);
            counter--;
        }
        scanner.close();
    }

}