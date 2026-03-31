import java.util.*;

class BookLibrary{
    public String ISBN;
    protected String title;
    private String author;

    BookLibrary(String ISBN, String title) {
        this.ISBN =ISBN;
        this.title =title;
    }

    //Setter for author
    void setAuthor(String author) {
        this.author =author;
    }

    //Getter for author
    String getAuthor() {
        return author;
    }

    void displayBook() {
        System.out.println("ISBN is- " +ISBN);
        System.out.println("Title is- " +title);
        System.out.println("Author is- " +author);
    }
    
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter ISBN, title, author and file size- ");
        String isbn =scanner.nextLine();
        String title = scanner.nextLine();
        String author =scanner.nextLine();
        double fileSize = scanner.nextDouble();

        EBook ebook = new EBook(isbn, title, fileSize);
        ebook.setAuthor(author);

        System.out.println("Book Details are- ");
        ebook.displayBook();
        System.out.println();

        System.out.println("EBook Details after demostrating access modifiers- ");
        ebook.displayEBook();

        scanner.close();
    }
}

class EBook extends BookLibrary {
    private double fileSize;

    EBook(String ISBN, String title, double fileSize) {
        super(ISBN, title);
        this.fileSize = fileSize;
    }

    //demonstrating access modifiers
    void displayEBook() {
        System.out.println("EBook ISBN is- " +ISBN);
        System.out.println("EBook Title is- " +title);

        //to access private author must use getter
        System.out.println("EBook Author is- " +getAuthor());
        System.out.println("File Size is- " +fileSize+ " MB");
    }
}