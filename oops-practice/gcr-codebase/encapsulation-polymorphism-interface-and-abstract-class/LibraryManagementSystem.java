public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryItem item1 = new Book(1, "Java Basics", "James");
        LibraryItem item2 = new Magazine(2, "Tech World", "Editor");
        LibraryItem item3 = new DVD(3, "Movie DVD", "Director");

        item1.getItemDetails();
        System.out.println("Loan Duration is- " + item1.getLoanDuration());
        ((Book)item1).reserveItem();
        
        System.out.println();
        item2.getItemDetails();
        System.out.println("Loan Duration is- " + item2.getLoanDuration());

        System.out.println();
        item3.getItemDetails();
        System.out.println("Loan Duration is- " + item3.getLoanDuration());
    }
}

//Abstract class
abstract class LibraryItem {
    int itemId;
    String title;
    String author;

    LibraryItem(int itemId, String title, String author) {
        this.itemId =itemId;
        this.title =title;
        this.author =author;
    }

    // Abstract method
    public abstract int getLoanDuration();
    
    public void getItemDetails() {
        System.out.println("ID is- " + itemId + "\n Title is- " + title + "\n Author is- " + author);
    }
}

//interface
interface Reservable {
    void reserveItem();
    boolean checkAvailability();
}

// Book
class Book extends LibraryItem implements Reservable {
    boolean isAvailable = true;

    Book(int itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 30;
    }

    @Override
    public void reserveItem() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Book reserved successfully!");
        } else {
            System.out.println("Book not available!");
        }
    }

    @Override
    public boolean checkAvailability() {
        return isAvailable;
    }
}

// Magazine
class Magazine extends LibraryItem {
    public Magazine(int itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 10;
    }
}

// DVD
class DVD extends LibraryItem {
    public DVD(int itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 5;
    }
}
