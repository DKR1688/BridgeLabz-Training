package LinkedList;
//Circular Linked List: Online Ticket Reservation System

public class OnlineTicketReservation {
	public static void main(String[] args) {
		TicketReservationSystem system = new TicketReservationSystem();

		system.addTicket(101, "Gaurav", "Inception", "A1", "10:00 AM");
		system.addTicket(102, "Rohit", "Avatar", "B2", "11:00 AM");
		system.addTicket(103, "Gaurav", "Inception", "A2", "10:05 AM");

		System.out.println("\nAll Tickets- ");
		system.displayTickets();

		System.out.println("\nSearch by Customer- ");
		system.searchByCustomer("Gaurav");

		System.out.println("\nSearch by Movie- ");
		system.searchByMovie("Avatar");

		System.out.println("\nTotal Tickets Booked- " + system.countTickets());

		System.out.println("\nRemove Ticket- ");
		system.removeTicket(102);

		System.out.println("\nUpdated Ticket List- ");
		system.displayTickets();
	}
}

//Node class to store ticket details
class TicketNode {
	int ticketId;
	String customerName;
	String movieName;
	String seatNumber;
	String bookingTime;
	TicketNode next;

	TicketNode(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
		this.ticketId = ticketId;
		this.customerName = customerName;
		this.movieName = movieName;
		this.seatNumber = seatNumber;
		this.bookingTime = bookingTime;
		this.next = null;
	}
}

//Class that manages ticket booking using Circular Linked List
class TicketReservationSystem {
	private TicketNode head = null;

	//Add ticket at the end of the circular list
	public void addTicket(int id, String customer, String movie, String seat, String time) {
		TicketNode newNode = new TicketNode(id, customer, movie, seat, time);

		if (head == null) {
			head = newNode;
			newNode.next = head;
			return;
		}

		TicketNode temp = head;
		while (temp.next != head) {
			temp = temp.next;
		}

		temp.next = newNode;
		newNode.next = head;
	}

	// Remove ticket by Ticket ID
	public void removeTicket(int id) {
		if (head == null) {
			System.out.println("No tickets booked.");
			return;
		}

		TicketNode curr = head;
		TicketNode prev = null;

		do {
			if (curr.ticketId == id) {
				// Only one node
				if (curr == head && curr.next == head) {
					head = null;
				}
				// Removing head
				else if (curr == head) {
					TicketNode last = head;
					while (last.next != head) {
						last = last.next;
					}
					head = head.next;
					last.next = head;
				}
				// Removing middle or last
				else {
					prev.next = curr.next;
				}
				System.out.println("Ticket removed successfully.");
				return;
			}
			prev = curr;
			curr = curr.next;
		} while (curr != head);

		System.out.println("Ticket not found.");
	}

	// Display all tickets
	public void displayTickets() {
		if (head == null) {
			System.out.println("No tickets to display.");
			return;
		}

		TicketNode temp = head;
		do {
			displayTicket(temp);
			temp = temp.next;
		} while (temp != head);
	}

	//Search by Customer Name
	public void searchByCustomer(String name) {
		if (head == null) {
			System.out.println("No tickets booked.");
			return;
		}

		TicketNode temp = head;
		boolean found = false;

		do {
			if (temp.customerName.equalsIgnoreCase(name)) {
				displayTicket(temp);
				found = true;
			}
			temp = temp.next;
		} while (temp != head);

		if (!found)
			System.out.println("No tickets found for customer: " + name);
	}

	// 5. Search by Movie Name
	public void searchByMovie(String movie) {
		if (head == null) {
			System.out.println("No tickets booked.");
			return;
		}

		TicketNode temp = head;
		boolean found = false;

		do {
			if (temp.movieName.equalsIgnoreCase(movie)) {
				displayTicket(temp);
				found = true;
			}
			temp = temp.next;
		} while (temp != head);

		if (!found)
			System.out.println("No tickets found for movie: " + movie);
	}

	//Count total tickets
	public int countTickets() {
		if (head == null)
			return 0;

		int count = 0;
		TicketNode temp = head;
		do {
			count++;
			temp = temp.next;
		} while (temp != head);

		return count;
	}

	//Helper method to display single ticket details
	private void displayTicket(TicketNode t) {
		System.out.println("Ticket ID: " + t.ticketId + ", Customer: " + t.customerName + ", Movie: " + t.movieName
				+ ", Seat: " + t.seatNumber + ", Time: " + t.bookingTime);
	}
}