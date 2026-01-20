package BookShelf;

public class BookShelf {
	public static void main(String[] args) {
        Shelf shelf = new Shelf();

        Book b1 = new Book("The Hobbit", "J.R.R. Tolkien");
        Book b2 = new Book("1984", "George Orwell");
        Book b3 = new Book("The Hobbit", "J.R.R. Tolkien"); // duplicate

        shelf.addBook("Fantasy", b1);
        shelf.addBook("Dystopian", b2);
        shelf.addBook("Fantasy", b3); // duplicate check

        shelf.showCatalog();

        shelf.borrowBook("Fantasy", b1);
        shelf.showCatalog();

        shelf.returnBook("Fantasy", b1);
        shelf.showCatalog();
	}
}
