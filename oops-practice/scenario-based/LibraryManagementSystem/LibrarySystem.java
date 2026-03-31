package LibraryManagementSystem;

import java.util.*;
public class LibrarySystem{
    public static void main(String[] args) {
        //initialize books in an array
        Book[] books = {
        		new Book("Let Us C", "Yashavant Kanetkar"),
        	    new Book("Data Structures Through C", "Yashavant Kanetkar"),
        	    new Book("Operating System Concepts", "Silberschatz, Galvin, Gagne"),
        	    new Book("Database System Concepts", "Abraham Silberschatz, Henry F. Korth, S. Sudarshan"),
        	    new Book("Computer Networks", "Andrew S. Tanenbaum, David J. Wetherall"),
        };

        Library library = new Library(books);
        Scanner scanner= new Scanner(System.in);

        while (true) {
            System.out.println("Library Menu to fetch file---");
            System.out.println("1. Show all books");
            System.out.println("2. Search by title");
            System.out.println("3. Checkout a book");
            System.out.println("4. Return a book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    library.showBooks();
                    break;
                case 2:
                    System.out.print("Enter part of title- ");
                    library.search(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter exact title to checkout- ");
                    try {
                        System.out.println(library.checkout(scanner.nextLine()) ? "Checkout successful!" : "Checkout failed.");
                    } catch (BookNotAvailableException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter exact title to return- ");
                    System.out.println(library.returnBook(scanner.nextLine()) ? "Return successful!" : "Return failed.");
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}