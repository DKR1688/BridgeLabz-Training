public class LibraryBook {
	public static void main(String[] args) {
        Book2 book = new Book2("RD Sharma", "Dr. RD", 900, true);
        book.displayBook();
        book.borrowBook();
        book.displayBook();
    }
}

class Book2{
	String title;
	String author;
	double price;
	boolean availability;
	
	public Book2(String title, String author, double price, boolean availability) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.availability = availability;
    }

	//this method is to borrow a book
    public void borrowBook() {
        if (availability) {
            availability =false;
            System.out.println("You have successfully borrowed- " +title);
        } else {
            System.out.println("We are sorry, the book " +title+" is unavailable");
        }
    }
    
    public void displayBook() {
        System.out.println("Title is- " +title+"\n Author is- " +author+"\n Price is- " +price+
        					"\n Available or not available- "+(availability ? "Yes" : "No"));
        System.out.println();
    }
}