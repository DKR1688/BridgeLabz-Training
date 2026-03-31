// a program to find the factors of a number taken as user input.

import java.util.*;
public class FactorsOfNumberUsingFor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        // here we are finding factors of the number
        if (number<=0) {
            System.out.println("Factors does not exist for negative numbers or zero");
        } else {
            // printing the factors of number
            for (int i = 1; i < number; i++) {

                // checking divisibility
                if (number%i == 0) {
                    System.out.println(i);
                }
            }
        }
        scanner.close();
    }
}