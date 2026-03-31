// a program FizzBuzz, take a number as user input, and 
// check for a positive integer. If positive integer, loop and 
// print the number, but for multiples of 3 print "Fizz" instead of the number, 
// for multiples of 5 print "Buzz", and for multiples of both print "FizzBuzz".


import java.util.*;
public class FizzBuzzNumberUsingFor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number =scanner.nextInt();

        // here we check for positive integer
        if (number<=0) {
            System.out.println("It is not a positive integer.");
        } else {

            // here we run the loop from 1 to user input number
            for (int i=1; i<=number; i++) {

                // if i is multiple of both 3 and 5 then print FizzBuzz
                if (i%3==0 && i%5==0) {
                    System.out.println("FizzBuzz");
                } else if (i%3 == 0) {
                    System.out.println("Fizz");
                } else if (i%5 == 0) {
                    System.out.println("Buzz");
                } else {

                    // here we print the number because it is not multiple of 3 or 5, it's positive
                    System.out.println(i);
                }
            }
        }
        scanner.close();
    }
}
