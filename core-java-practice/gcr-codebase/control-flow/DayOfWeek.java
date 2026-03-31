//  a program DayOfWeek that takes a date as input and 
// prints the day of the week that the date falls on.

public class DayOfWeek {
    public static void main(String[] args) {
        
        if (args.length!=3) {
            System.out.println("Usage: java DayOfWeek <month> <day> <year>");
            return;
        }

        // taking three command-line arguments: m (month), d (day), and y (year).
        int month = Integer.parseInt(args[0]);
        int day = Integer.parseInt(args[1]);
        int year =Integer.parseInt(args[2]);

        // here we are using formula to calculate the day of the week given in question
        int y0 = year-(14-month)/12;
        int x = y0 + y0/4 - y0/100 + y0/400;
        int m0 = month+12 * ((14-month) / 12)-2;
        int d0 = (day+x + (31*m0) / 12)%7;

        // here we print the day of the week
        System.out.println("Day of the week is " + d0);
    }
}