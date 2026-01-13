package HotelReservationSystem;

public class HotelReservationSystem {

	public static void main(String[] args) {
		
		Room room1 = new StandardRoom(555);
		Room room2 = new DeluxeRoom(364);
		
		Guest guest1 = new Guest(101,"Deepak");
		
		Reservation reservation = new Reservation(room1,guest1,5);
		
		try {
			reservation.checkIn();
			
		}catch(RoomNotAvailableException e) {
			System.out.println("Exception occured- "+e.getMessage());
		}
		PricingStrategy strategy = new PeakSeasonPricing();
		reservation.checkOut(strategy);
		reservation.displayDetails();
	}

}
