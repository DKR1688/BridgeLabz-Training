package SmartUniversityLibrary;

//strategy for reservation
public interface ReservationStrategy {
	void reserve(Book book, User user);
}

class StandardReservation implements ReservationStrategy {
	public void reserve(Book book, User user) {
		Logger.getInstance().log(user.getName() + " reserved "+book.getTitle() + " (Standard)");
	}
}

class PriorityReservation implements ReservationStrategy {
	public void reserve(Book book, User user) {
		Logger.getInstance().log(user.getName() + " reserved "+book.getTitle() + " (Priority)");
	}
}
