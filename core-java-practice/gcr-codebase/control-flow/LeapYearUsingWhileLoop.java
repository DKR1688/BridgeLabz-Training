// a program to determine Leap Year with single 
// if condition using logical and && and or || operators

import java.util.*;;
public class LeapYearUsingWhileLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // here we are taking year input from user
        int year = scanner.nextInt();

        // if the year is valid for Gregorian calendar then only we will check for leap year
        if (year<1582) {
            System.out.println("Year should be 1582 or greater than 1582.");
        } else {

            // here a leap year using single if condition with logical operators
            if ((year%4 == 0 && year%100 != 0) || (year%400 == 0)) {
                System.out.println(year+" is a Leap Year.");
            } else {
                System.out.println(year+" is not a Leap Year.");
            }
        }
        scanner.close();
    }
}