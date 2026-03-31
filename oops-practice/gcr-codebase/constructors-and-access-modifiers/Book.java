public class Book {
	String title;
	String author;
	int price;
	
	//Constructor without parameter
	Book(){
		this.title =" ";
		this.author =" ";
		this.price =0;
	}
	
	//Constructor with parameter
	Book(String title, String author, int price){
		this.title =title;
		this.author =author;
		this.price =price;
	}
	
	void displayBook() {
		System.out.println("Title is- "+title);
		System.out.println("Author is- "+author);
		System.out.println("Price is- "+price);
		System.out.println();
	}
	
	public static void main(String[] args) {
		//calling default 
		Book book1 =new Book();
		book1.displayBook();
		
		//calling parameterized
		Book book2 =new Book("RD Sharma", "Dr. RD sharma", 900);
		book2.displayBook();
	}
}
