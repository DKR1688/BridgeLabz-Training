package methods;

import java.util.*;
public class Calendar{
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the month and year ");
        int month = scanner.nextInt();
        int year = scanner.nextInt();
        getCalendar(month, year);
        scanner.close();
    }
	
	// a Method to get the name of the month. For this define a month Array to store the names of the months
    static String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    
    //a Method to get the number of days in the month. For this define a days Array to store the number of days in each month. 
    //For Feb month, check for Leap Year to get the number of days. Also, define a Leap Year Method. 
    public static boolean isLeap(int year) {
        return (year%400 == 0) || (year% 4 == 0 && year%100 != 0);
    }
    public static int daysInMonth(int month, int year) {
        int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
        
        if (month==2) {
        	return isLeap(year) ? 29:28;
        }
        return days[month-1];
    }
    
    // a method to get the first day of the month using the Gregorian calendar algorithm
    public static int firstDayOfMonth(int month, int year) {
        int a= 1;
        if (month<3) { 
        	month+= 12; 
        	year-= 1; 
        }
        
        int b= year% 100;
        int c = year/ 100;
        int d = (a + (13*(month+1))/5 + b + (b/4) + (c/4) + 5*c) % 7;
        int day = (d + 6) % 7;
        return day;
    }
    
    //a method to display calendar
    public static void getCalendar(int month, int year) {
        System.out.println("    " + months[month-1] + " " + year);
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");
        
        int first = firstDayOfMonth(month, year);
        int days = daysInMonth(month, year);
        
        //The first for loop up to the first day to get the proper indentation. 
        //As in the example above 3 spaces from Sun to Thu as to be set as July 1st starts on Fri
        for (int i=0; i<first; i++) {
        	System.out.print("    ");
        }
        
        //The Second for loop displays the days of the month starting from 1 to 
        //the number of days. Add proper indentation for single-digit days using %3d to 
        //display the integer right-justified in a field of width 3. Please note to move to the next line after Sat
        int dow = first;
        for (int day=1; day<=days; day++) {
            System.out.printf("%3d ", day);
            dow++;
            if (dow == 7) { 
            	System.out.println(); 
            	dow = 0;
            }
        }
        if (dow != 0) {
        	System.out.println();
        }
    }
}