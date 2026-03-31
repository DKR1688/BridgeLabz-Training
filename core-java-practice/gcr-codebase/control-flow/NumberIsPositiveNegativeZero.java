// a program to check whether a number is positive, negative, or zero.

import java.util.Scanner;
public class NumberIsPositiveNegativeZero {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number= scanner.nextInt();

        // here we are checking if the number is positive
        if (number>0) {
            System.out.println("The number "+number+" is positive.");
        }
        // here we are checking if the number is negative
        else if (number<0) {
            System.out.println("The number "+number+" is negative.");
        }
        // here the number will be zero
        else {
            System.out.println("The number is zero.");
        }
        scanner.close();
    }
}
