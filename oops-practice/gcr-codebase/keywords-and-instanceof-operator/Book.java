//Library Management System
class Book {
    //A static variable libraryName shared across all books.
    static String libraryName ="Egmore Library";
    
    //A static method displayLibraryName() to print the library name.
    static void displayLibraryName() {
        System.out.println("Library name is- " +libraryName);
    }
    
    String title;
    String author;
    
    //Use a final variable isbn to ensure the unique identifier of a book cannot be changed.
    final String isbn;

    //Use this to initialize title, author, and isbn in the constructor.
    Book(String title, String author, String isbn) {
        this.title =title;
        this.author =author;
        this.isbn =isbn;
    }

    //Verify if an object is an instance of the Book class before displaying its details.
    void displayBookDetails(Object object) {
        if (object instanceof Book) {
            System.out.println("Title is- " +title);
            System.out.println("Author is- " +author);
            System.out.println("ISBN is- " +isbn);
        } else {
            System.out.println("Not a valid Book object.");
        }
    }
    
    public static void main(String[] args) {
        Book book1 = new Book("Effective Java", "Joshua Bloch", "978-0134685991");

        //displaying library name
        Book.displayLibraryName();

        //displaying book details using instanceof book
        book1.displayBookDetails(book1);
    }
}