package MovieScheduleManager;

import java.util.Scanner;
import java.util.regex.Pattern;
public class MovieUtility {
	private Movie[] movies = new Movie[50];
    private int movieCount = 0;
    private Scanner scanner = new Scanner(System.in);

    // Add a movie
    public void addMovie() throws InvalidTimeFormatException {
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Time (HH:MM): ");
        String time = scanner.nextLine();

        if (movieCount >= movies.length) {
            throw new IndexOutOfBoundsException("Capacity is full");
        } else {
            // Regex validation for 24-hour format
            if (!Pattern.matches("^([01]\\d|2[0-3]):([0-5]\\d)$", time)) {
                throw new InvalidTimeFormatException("'" + time + "' is not a valid 24-hour time format.");
            }

            movies[movieCount++] = new Movie(title, time);
            System.out.println("-------Movie added successfully-------");
        }
    }

    public void searchMovies() {
        System.out.print("Please enter movie to search: ");
        String keyword = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < movieCount; i++) {
            if (movies[i].getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(movies[i].toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No match found");
        }
    }

    // Display all movies
    public void displayAll() {
        if (movieCount == 0) {
            System.out.println("No movie scheduled");
        } else {
            for (int i = 0; i < movieCount; i++) {
                System.out.println(movies[i]);
            }
        }
    }
}
