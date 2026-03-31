package LibraryManagementSystem;

//Book class
class Book {
	String title, author;
	boolean checkedOut;

	Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.checkedOut = false;
	}

	public String toString() {
		return title + " by " + author + " [" + (checkedOut ? "Checked Out" : "Available") + "]";
	}
}