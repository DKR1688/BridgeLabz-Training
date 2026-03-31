/*Problem 2: Date Arithmetic Create a program that:
➢ Takes a date input and adds 7 days, 1 month, and 2 years to it.
➢ Then subtracts 3 weeks from the result.
Hint: Use LocalDate.plusDays(), plusMonths(), plusYears(), and
minusWeeks() methods.
*/

import java.util.*;
import java.time.*;
public class DateArithmetic {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date as YYYY-MM-DD - ");
        String date = scanner.nextLine();

        //Parsing input into LocalDate
        LocalDate localDate = LocalDate.parse(date);

        LocalDate ans= localDate.plusDays(7).plusMonths(1).plusYears(2).minusWeeks(3);

        System.out.println("After performing all operations final date is - " +ans);
        scanner.close();
    }
}
