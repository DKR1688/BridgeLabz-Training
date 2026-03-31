package MovieScheduleManager;

import java.util.*;
public class ScheduleManager {
    public static void main(String[] args) {
        MovieUtility utility = new MovieUtility();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Cinema Time Manager ---");
            System.out.println("Press 1. Add Movie");
            System.out.println("Press 2. View All Movies");
            System.out.println("Press 3. Search Movie");
            System.out.println("Press 4. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        utility.addMovie();
                        break;
                    case 2:
                        utility.displayAll();
                        break;
                    case 3:
                        utility.searchMovies();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage());
            } catch (InvalidTimeFormatException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected Error- " + ex.getMessage());
            }
        }
    }
}