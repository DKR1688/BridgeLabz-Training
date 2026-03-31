package methods;

import java.util.*;
public class LeapYear {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter a year ");
        int year =scanner.nextInt();

        if (isLeapYear(year)) {
        	System.out.println("It is a Leap Year");
        }
        else{
        	System.out.println("It is not a Leap Year");
        }
        scanner.close();
    }
	
	//a method to check for Leap Year
	public static boolean isLeapYear(int year) {
		
	    //LeapYear program only works for year >= 1582, 
		//corresponding to a year in the Gregorian calendar. 
	    if (year<1582) {
	    	return false;
	    }
	    //Leap year is divisible by 4 and not divisible by 100 or divisible by 400
	    return (year%4 == 0 && year%100 != 0) || (year%400 == 0);
	   }
}
