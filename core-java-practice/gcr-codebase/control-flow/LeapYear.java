// a LeapYear program that takes a year as input and 
// outputs the Year is a Leap Year or not a Leap Year. 
//  it is having multiple if else statements based on conditions provided in question,
// and a second part having only one if statement and multiple logical 


import java.util.Scanner;
public class LeapYear {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        int year = scanner.nextInt();

        // The LeapYear program only works for year >= 1582, 
        // corresponding to a year in the Gregorian calendar. 
        // So ensure to check for the same. 
        if (year<1582) {
            System.out.println("Year should be 1582 or greater than 1582.");
        } else {

            //the Leap Year is a Year divisible by 4 
            // and not 100 unless it is divisible by 400. 
            if (year%4 == 0) {
                if (year%100 == 0) {
                    if (year%400 == 0) {
                        System.out.println(year+" is a Leap Year.");
                    } else {
                        System.out.println(year+" is not a Leap Year.");
                    }
                } else {
                    System.out.println(year+" is a Leap Year.");
                }
            } else {
                System.out.println(year+" is not a Leap Year.");
            }
        }

        // a second part having only one if statement and multiple logical 
        // to find if a year is a leap year or not.
        if (year>= 1582 && ((year%4 == 0 && year%100 != 0) || (year%400 == 0))) {
            System.out.println(year+ " is a Leap Year.");
        } else if (year>=1582) {
            System.out.println(year+ " is not a Leap Year.");
        }
        scanner.close();
    }
}