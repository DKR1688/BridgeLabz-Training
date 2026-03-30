// aprogram to find the factors of a number using the while loop

import java.util.*;
public class FactorsOfNumberUsingWhile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        // here we are finding factors of the number
        if (number<=0) {
            System.out.println("Factors does not exist for negative numbers or zero");
        } else {
            // printing the factors of number
            int i = 1;
            while (i<number) {

                // checking divisibility
                if (number%i == 0) {
                    System.out.println(i);
                }
                i++;
            }
        }
        scanner.close();
    }
}