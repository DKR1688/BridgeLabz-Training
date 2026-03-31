/*Date Formatting Write a program that:
➢ Displays the current date in three different formats:
■ dd/MM/yyyy
■ yyyy-MM-dd
■ EEE, MMM dd, yyyy

Hint: Use DateTimeFormatter with custom patterns for date formatting.*/

import java.time.*;
import java.time.format.*;
public class DateFormatting {
	public static void main(String[] args) {
		//first taking local date
		LocalDate date =LocalDate.now();
		
		//In first format
		DateTimeFormatter timeFormatter1 =DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formate1 =date.format(timeFormatter1);
		
		//In second format
		DateTimeFormatter timeFormatter2 =DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formate2 =date.format(timeFormatter2);
		
		//In third format
		DateTimeFormatter timeFormatter3 =DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
		String formate3 =date.format(timeFormatter3);
		
		System.out.println("The current date in three diffrent formats are - \n "+formate1+" \n "+formate2+" \n "+formate3);
	}
}
