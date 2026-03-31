public class MovieTicket {
	String movieName;
    String seatNumber;
    int price;
    boolean ticketBooked;

    public MovieTicket(String movieName, String seatNumber, int price, boolean ticketBooked) {
    	this.movieName =movieName;
        this.seatNumber =seatNumber;
        this.price = price;
        this.ticketBooked =ticketBooked;
    }

    //this method is to book a ticket
    public void bookTicket() {
        if (ticketBooked) {
            System.out.println("Ticket booked for movie- " +movieName);
            System.out.println("Seat Number- " +seatNumber);
            System.out.println("Price- $" + price);
        } else {
            System.out.println("House full! sorry... Ticket already booked");
        }
    }
    
    //this method is to display ticket details
    public void displayTicket() {
        if (ticketBooked) {
        	bookTicket();
        } else {
        	System.out.println("Ticket have not booked yet...");
        }
    }
    
    public static void main(String[] args) {
        MovieTicket movie1 = new MovieTicket("Dragon", "A10", 120, true);
        MovieTicket movie2 = new MovieTicket("Dragon", "A10", 120, false);

        //Displaying before booking
        movie2.displayTicket();

        //Booking the ticket
        movie1.bookTicket();

        //Trying booking again but it should fail to output
        movie2.bookTicket();
        movie2.bookTicket();

        //Displaying after booking
        movie1.displayTicket();
    }
}
