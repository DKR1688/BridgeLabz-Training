import java.util.*;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        //initialize books in an array
        Book[] books = {
            new Book("Clean Code", "Robert C. Martin"),
            new Book("Introduction to Algorithms", "Cormen et al."),
            new Book("Effective Java", "Joshua Bloch"),
            new Book("The Pragmatic Programmer", "Andrew Hunt, David Thomas"),
            new Book("Design Patterns", "Erich Gamma et al.")
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
                    System.out.println(library.checkout(scanner.nextLine()) ? "Checkout successful!" : "Checkout failed.");
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
class Book {
    String title, author;
    boolean checkedOut;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
    }

    public String toString() {
        return title +" by "+ author +" [" +(checkedOut ? "Checked Out" : "Available") +"]";
    }
}

class Library {
    Book[] books;

    Library(Book[] books) {
        this.books = books;
    }

    void showBooks() {
        System.out.println("Library Books are- ");
        for (Book b : books) {
        	System.out.println(b);
        }
    }

    void search(String query) {
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(query.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
        	System.out.println("No match found.");
        }
    }

    boolean checkout(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title) && !b.checkedOut) {
                b.checkedOut = true;
                return true;
            }
        }
        return false;
    }

    boolean returnBook(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title) && b.checkedOut) {
                b.checkedOut = false;
                return true;
            }
        }
        return false;
    }
}