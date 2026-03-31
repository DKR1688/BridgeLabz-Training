package strings;

import java.util.Scanner;
public class CalendarOfMonthYear {
	
	// a Method to get the name of the month and no of days in month
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};

    //a Method check for Leap Year to get the number of days
    public static boolean isLeapYear(int year) {
        return (year%4==0 && year%100 != 0) || (year%400 == 0);
    }

    //a Method to get the number of days in the month. 
    public static int getDaysInMonth(int month, int year) {
        if (month==2 && isLeapYear(year)) {
        	return 29;
        }
        return daysInMonth[month-1];
    }

    //a method to get the first day of the month using the Gregorian calendar algorithm
    public static int getFirstDay(int month, int year) {
        int y = year-(14-month)/12;
        int x = y+y/4-y/100+y/400;
        int m = month+12*((14 - month)/12)-2;
        return (1 + x + (31*m)/12) % 7;
    }

    // a Method to display calendar
    public static void displayCalendar(int month, int year) {
        System.out.println("     " + months[month - 1] + " " + year);
        System.out.println("Su Mo Tu We Th Fr Sa");

        int firstDay = getFirstDay(month, year);
        int numDays = getDaysInMonth(month, year);

        //this for loop up to the first day to get the proper indentation. As in 
        //the example above 3 spaces from Sun to Thu as to be set as July 1st starts on Fri
        for (int i=0; i<firstDay; i++) {
        	System.out.print("   ");
        }

        //this Second for loop Displays the days of the month starting from 1 to the number of days. 
        //Add proper indentation for single-digit days using %3d to display the integer right-justified in a field of width 3. Please note to move to the next line after Sat
        for (int day=1; day<=numDays; day++) {
            System.out.println(day);
            if ((day + firstDay) % 7 == 0) {
            	System.out.println();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter month between 1-12 ");
        int month = scanner.nextInt();
        System.out.println("Enter year ");
        int year = scanner.nextInt();

        displayCalendar(month, year);
        scanner.close();
    }
}