package LinkedList;
//Doubly Linked List: Movie Management System

public class MovieManagementSystem{
	public static void main(String[] args) {
		MovieList list = new MovieList();

		//adding movies
		list.addAtBeginning("Inception", "Nolan", 2010, 9.0);
		list.addAtEnd("Interstellar", "Nolan", 2014, 8.6);
		list.addAtPosition(2, "The Dark Knight", "Nolan", 2008, 9.1);

		System.out.println("Forward Display- ");
		list.displayForward();

		System.out.println("Backward Display- ");
		list.displayBackward();
		System.out.println();

		//searching movie with director name or rating
		System.out.println("Search by Director- ");
		list.searchByDirector("Nolan");

		System.out.println("Search by Rating- ");
		list.searchByRating(9.0);
		System.out.println();
		
		//updating rating
		System.out.println("Update Rating- ");
		list.updateRating("Interstellar", 9.2);
		System.out.println();

		//removing movie
		System.out.println("Remove Movie- ");
		list.removeByTitle("Inception");
		System.out.println();

		//displaying all changes
		System.out.println("Forward Display- ");
		list.displayForward();
	}
}

class MovieNode {
	String title;
	String director;
	int year;
	double rating;
	MovieNode next;
	MovieNode prev;

	MovieNode(String title, String director, int year, double rating) {
		this.title =title;
		this.director =director;
		this.year =year;
		this.rating =rating;
		this.next =null;
		this.prev =null;
	}
}

class MovieList {
	MovieNode head;
	MovieNode tail;

	void addAtBeginning(String title, String director, int year, double rating) {
		MovieNode newMovie = new MovieNode(title, director, year, rating);
		if (head==null) {
			head=tail = newMovie;
		} else {
			newMovie.next =head;
			head.prev =newMovie;
			head =newMovie;
		}
	}

	void addAtEnd(String title, String director, int year, double rating) {
		MovieNode newMovie = new MovieNode(title, director, year, rating);
		if (head ==null) {
			head =tail = newMovie;
		} else {
			tail.next = newMovie;
			newMovie.prev = tail;
			tail =newMovie;
		}
	}

	void addAtPosition(int position, String title, String director, int year, double rating) {
		if (position<=1) {
			addAtBeginning(title, director, year, rating);
			return;
		}
		MovieNode temp = head;
		for (int i=1; i<position-1; i++) {
			temp =temp.next;
		}
		if (temp ==null || temp.next ==null) {
			addAtEnd(title, director, year, rating);
			return;
		}
		MovieNode newMovie = new MovieNode(title, director, year, rating);
		newMovie.next = temp.next;
		newMovie.prev = temp;
		temp.next.prev = newMovie;
		temp.next = newMovie;
	}

	void removeByTitle(String title) {
		MovieNode temp =head;
		while (temp!=null && !temp.title.equals(title)) {
			temp =temp.next;
		}
		if (temp==null) {
			System.out.println("Movie not found.");
			return;
		}
		if (temp ==head) {
			head = head.next;
		}
		if (temp ==tail) {
			tail = tail.prev;
		}
		if (temp.prev != null) {
			temp.prev.next = temp.next;
		}
		if (temp.next != null) {
			temp.next.prev = temp.prev;
		}
		System.out.println("Movie " +title+ " removed.");
	}

	void searchByDirector(String director) {
		MovieNode temp =head;
		while (temp !=null) {
			if (temp.director.equals(director)) {
				System.out.println("Found- " +temp.title + " (" + temp.year + ") Rating- " + temp.rating);
			}
			temp = temp.next;
		}
	}

	void searchByRating(double rating) {
		MovieNode temp = head;
		while (temp != null) {
			if (temp.rating == rating) {
				System.out.println("Found- " + temp.title + " (" + temp.year + ") Director- " + temp.director);
			}
			temp = temp.next;
		}
	}

	void updateRating(String title, double newRating) {
		MovieNode temp =head;
		while (temp != null) {
			if (temp.title.equals(title)) {
				temp.rating = newRating;
				System.out.println("Rating updated for " + title + " to " + newRating);
				return;
			}
			temp = temp.next;
		}
		System.out.println("Movie not found.");
	}

	void displayForward() {
		MovieNode temp = head;
		while (temp != null) {
			System.out.println(temp.title + " | " + temp.director + " | " + temp.year + " | " + temp.rating);
			temp = temp.next;
		}
	}

	void displayBackward() {
		MovieNode temp = tail;
		while (temp != null) {
			System.out.println(temp.title+ " | " + temp.director + " | " + temp.year + " | " + temp.rating);
			temp = temp.prev;
		}
	}
}
