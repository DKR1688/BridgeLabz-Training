import java.util.*;
//custom Exception
class BookNotAvailableException extends Exception {
    BookNotAvailableException(String message) {
        super(message);
    }
}

public class LibraryManagementWithException{
    public static void main(String[] args) {
        //initialize books in an array
        BookStatus[] books = {
        		new BookStatus("Let Us C", "Yashavant Kanetkar"),
        	    new BookStatus("Data Structures Through C", "Yashavant Kanetkar"),
        	    new BookStatus("Operating System Concepts", "Silberschatz, Galvin, Gagne"),
        	    new BookStatus("Database System Concepts", "Abraham Silberschatz, Henry F. Korth, S. Sudarshan"),
        	    new BookStatus("Computer Networks", "Andrew S. Tanenbaum, David J. Wetherall"),
        };

        LibrarySystem library = new LibrarySystem(books);
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

// Book class
class BookStatus {
    String title, author;
    boolean checkedOut;

    BookStatus(String title, String author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
    }

    public String toString() {
        return title +" by "+ author +" [" +(checkedOut ? "Checked Out" : "Available") +"]";
    }
}

class LibrarySystem {
    BookStatus[] books;

    LibrarySystem(BookStatus[] books) {
        this.books = books;
    }

    void showBooks() {
        System.out.println("Library Books are- ");
        for (BookStatus b : books) {
            System.out.println(b);
        }
    }

    void search(String query) {
        boolean found = false;
        for (BookStatus b : books) {
            if (b.title.toLowerCase().contains(query.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No match found.");
        }
    }

    boolean checkout(String title) throws BookNotAvailableException {
        for (BookStatus b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                if (!b.checkedOut) {
                    b.checkedOut = true;
                    return true;
                } else {
                    throw new BookNotAvailableException("Book '" + title + "' is not available for checkout.");
                }
            }
        }
        return false;
    }

    boolean returnBook(String title) {
        for (BookStatus b : books) {
            if (b.title.equalsIgnoreCase(title) && b.checkedOut) {
                b.checkedOut = false;
                return true;
            }
        }
        return false;
    }
}