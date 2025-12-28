/*Date Comparison Write a program that:
➢ Takes two date inputs and compares them to check if the first date is before, after,
or the same as the second date.
Hint: Use isBefore(), isAfter(), and isEqual() methods from the LocalDate*/

import java.util.*;
import java.time.*;
public class DateComparison {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter two date to comparison as yyyy-mm-dd - ");
		String firstDate =scanner.nextLine();
		String secongDate = scanner.nextLine();
		
		// parsing dates into loacal dates
		LocalDate date1 = LocalDate.parse(firstDate);
		LocalDate date2 =LocalDate.parse(secongDate);
		
		if(date1.isBefore(date2)) {
			System.out.println("The first date comes before the second date.");
		}else if(date1.isAfter(date2)) {
			System.out.println("The firat date comes after the second date.");
		}else {
			System.out.println("Both dates are same.");
		}
		scanner.close();
	}
}
