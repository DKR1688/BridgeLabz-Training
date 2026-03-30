import java.util.*;

public class FlightBookingSystem {
    private Flight[] flights;              // Array for available flights
    private List<Booking> bookings;        // List for user bookings

    public FlightBookingSystem() {
        flights = new Flight[]{ new Flight("AI101", "Delhi", "Mumbai", 4500),
                				new Flight("AI202", "Delhi", "Bangalore", 5500),
                				new Flight("AI303", "Mumbai", "Chennai", 4000)};
        bookings = new ArrayList<>();
    }

    //adding a flight
    public void bookFlight(String passengerName, String flightNumber) {
        for (Flight f : flights) {
            if (f.flightNumber.equalsIgnoreCase(flightNumber)) {
                bookings.add(new Booking(passengerName, f));
                System.out.println("Booking successful for " + passengerName);
                return;
            }
        }
        System.out.println("Flight not found. Booking failed!");
    }

    //Displaying all bookings
    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            System.out.println("\nAll Bookings are---");
            for (Booking b : bookings) {
            	System.out.println(b);
            }
        }
    }

    //Searching flights
    public void searchFlights(String source, String destination) {
        boolean found = false;
        System.out.println("\nSearching flights from " + source + " to " + destination + "is- ");
        for (Flight f : flights) {
            if (f.source.equalsIgnoreCase(source) && f.destination.equalsIgnoreCase(destination)) {
                System.out.println(f);
                found = true;
            }
        }
        if (!found) {
        	System.out.println("No flights found!");
        }
    }

    //updating passenger’s flight
    public void updateBooking(String passengerName, String newFlightNumber) {
        for (Booking b : bookings) {
            if (b.passengerName.equalsIgnoreCase(passengerName)) {
                for (Flight f : flights) {
                    if (f.flightNumber.equalsIgnoreCase(newFlightNumber)) {
                        b.flight = f;
                        System.out.println("Booking updated for " + passengerName);
                        return;
                    }
                }
                System.out.println("New flight not found!");
                return;
            }
        }
        System.out.println("Booking not found for passenger- " + passengerName);
    }

    //Remove a booking
    public void deleteBooking(String passengerName) {
        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            if (it.next().passengerName.equalsIgnoreCase(passengerName)) {
                it.remove();
                System.out.println("Booking deleted for " + passengerName);
                return;
            }
        }
        System.out.println("Booking not found for passenger- " + passengerName);
    }

    public static void main(String[] args) {
        FlightBookingSystem system = new FlightBookingSystem();
        Scanner scanner= new Scanner(System.in);

        while (true) {
            System.out.println("\nFlight Booking System ---");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Bookings");
            System.out.println("4. Update Booking");
            System.out.println("5. Delete Booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice- ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Source- ");
                    String src = scanner.nextLine();
                    System.out.print("Destination- ");
                    String dest = scanner.nextLine();
                    system.searchFlights(src, dest);
                
                case 2:
                    System.out.print("Passenger Name- ");
                    String name = scanner.nextLine();
                    System.out.print("Flight Number- ");
                    String fno = scanner.nextLine();
                    system.bookFlight(name, fno);
                
                case 3:
                	system.displayBookings();
                case 4:
                    System.out.print("Passenger Name to Update- ");
                    String name2 = scanner.nextLine();
                    System.out.print("New Flight Number- ");
                    String fno2 = scanner.nextLine();
                    system.updateBooking(name2, fno2);
                
                case 5:
                    System.out.print("Passenger Name to Delete- ");
                    String name3 = scanner.nextLine();
                    system.deleteBooking(name3);
                
                case 6:
                    System.out.println("Exiting... Thank you!");
                    return;
                
                default:
                	System.out.println("Invalid choice!");
            }
        }
    }
}

class Flight {
    String flightNumber;
    String source;
    String destination;
    double price;

    Flight(String flightNumber, String source, String destination, double price) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    @Override
    public String toString() {
    	return "Flight details- " +"\n Number of flight- " +flightNumber +"\n Source of flight- "+source +
                "\n Destination of flight- " +destination+ "\n Price of flight- "+price;
    }
}

class Booking {
    String passengerName;
    Flight flight;

    Booking(String passengerName, Flight flight) {
        this.passengerName = passengerName;
        this.flight = flight;
    }

    @Override
    public String toString() {
    	return "Booking dtails- " +"\n Passenger name- " +passengerName +
                "\n Flight of passenger- " +flight;
    }
}
