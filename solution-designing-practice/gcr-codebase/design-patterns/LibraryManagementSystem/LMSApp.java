package LibraryManagementSystem;

public class LMSApp {
	public static void main(String[] args) {
        //singleton
        LibraryCatalog catalog = LibraryCatalog.getInstance();

        //factory
        User student = UserFactory.createUser("student", "Alice");
        User faculty = UserFactory.createUser("faculty", "Dr. Bob");
        User guest = UserFactory.createUser("guest", "Charlie");

        //observer registration
        catalog.addObserver(student);
        catalog.addObserver(faculty);
        catalog.addObserver(guest);

        student.showRole();
        faculty.showRole();
        guest.showRole();

        //builder
        Book book1 = new Book.BookBuilder("Design Patterns")
        			.author("GoF")
        			.edition("2nd")
        			.genre("Software Engineering")
        			.build();

        catalog.addBook(book1);

        //strategy
        ReservationStrategy standard=new StandardReservation();
        ReservationStrategy priority=new PriorityReservation();

        standard.reserve(book1, student);
        priority.reserve(book1, faculty);
        standard.reserve(book1, guest);
    }
}
