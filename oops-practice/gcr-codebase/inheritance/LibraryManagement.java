public class LibraryManagement {
	public static void main(String[] args) {
        Author a1 = new Author("Java Programming", 2022, "James Gosling", "Father of Java");
        a1.displayInfo();
    }
}

//Superclass
class Book {
	String title;
	int publicationYear;

	public Book(String title, int publicationYear) {
		this.title =title;
		this.publicationYear =publicationYear;
	}

	public void displayInfo() {
		System.out.println("Title of bokk- " +title+ ",\n Publication year of book- " +publicationYear);
	}
}

class Author extends Book {
	String name;
	String bio;

	public Author(String title, int publicationYear, String name, String bio) {
		super(title, publicationYear);
		this.name =name;
		this.bio =bio;
 }

	@Override
	public void displayInfo() {
		super.displayInfo();
		System.out.println("Author name is- " +name + ", Bio is- "+bio);
	}
}
