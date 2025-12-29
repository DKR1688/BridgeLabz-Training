/*Rohan’s Library Reminder App 📚
Rohan wants a fine calculator:
● Input return date and due date.
● If returned late, calculate fine: ₹5/day.
● Repeat for 5 books using for-loop.*/

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class LibraryReminder {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        int finePerDay = 5;
        int totalFine = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i=1; i<=5; i++) {
            System.out.println("Book " +i+ " - ");
            
            // Input due date and return date
            System.out.print("Enter due date and return date as yyyy-mm-dd - ");
            String dueDateStr = scanner.nextLine();
            LocalDate dueDate =LocalDate.parse(dueDateStr, formatter);
            String returnDateStr = scanner.nextLine();
            LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);


            int fine = 0;
            if (returnDate.isAfter(dueDate)) {
            	long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);
                fine = (int) lateDays*finePerDay;
                System.out.println("Total fine after due date to return a book is - " +fine);
            } else {
                System.out.println("Returned on time. No fine.");
            }

            totalFine += fine;
        }

        System.out.println("Total Fine for all books is - " +totalFine);
        scanner.close();
    }
}
