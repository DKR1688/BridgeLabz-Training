/*Movie Ticket Booking App 🎬
Ask users for movie type, seat type (gold/silver), and snacks.
● Use switch and if together.
● Loop through multiple customers.
● Clean structure and helpful variable names.*/

import java.util.*;
public class MovieTicketBooking{
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        System.out.print("Enter number of customers - ");
        int totalCustomers =scanner.nextInt();

        for (int i= 1; i<=totalCustomers; i++) {
            System.out.println("Booking for Customer "+i+ " -");

            System.out.println("Choose a movie type - 1. Action, 2. Comedy and 3. Drama - ");
            System.out.print("Enter your choice from 1 to 3 - ");
            int movieChoice = scanner.nextInt();

            String movieType;
            switch (movieChoice) {
                case 1:
                    movieType = "Action";
                    break;
                case 2:
                    movieType = "Comedy";
                    break;
                case 3:
                    movieType = "Drama";
                    break;
                default:
                    movieType = "Unknown";
                    System.out.println("Defaulted movies is drama.");
                    break;
            }

            //Ask users for movie type, seat type (gold/silver), and snacks.
            System.out.print("Enter your seat type Gold/Silver - ");
            String seatType = scanner.nextLine().trim().toLowerCase();

            int seatPrice;
            if (seatType.equals("gold")) {
                seatPrice=300;
            } else if (seatType.equals("silver")) {
                seatPrice=200;
            } else {
                seatPrice=200;
                seatType = "Silver";
                System.out.println("Default seat is silver.");
            }

            System.out.print("Do you want snacks? (yes/no) - ");
            String snackChoice =scanner.nextLine().trim().toLowerCase();

            int snackPrice=0;
            if (snackChoice.equals("yes")) {
                snackPrice=100;
            }

            int totalBill = seatPrice+snackPrice;

            System.out.println("Booking for Customer " +totalCustomers+ " -");
            System.out.println("Your movie is- " +movieType);
            System.out.println("Your seat is - " +seatType+" of"+seatPrice);
            System.out.println("Your snaks is - " +snackPrice);
            System.out.println("Your total Bill is - " +totalBill);
        }
        System.out.println("All bookings completed.");
        scanner.close();
    }
}
