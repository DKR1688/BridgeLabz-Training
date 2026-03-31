package HotelReservationSystem;

public class Reservation {
	private Room room;
	private Guest guest;
	private int days;
	
	public Reservation(Room room,Guest guest,int days) {
		this.room = room;
		this.guest = guest;
		this.days = days;
	}
	
	public void checkIn() throws RoomNotAvailableException{
		if(!room.isAvailable) {
			throw new RoomNotAvailableException("Room is not Available");
		}
		room.book();
		System.out.println("Check in successful for Guest: "+guest.getName());
	}
	
	public void checkOut(PricingStrategy strategy) {
		double totalCost = days*room.calculatePrice(strategy);
		System.out.println("Invoice: ");
		System.out.println("GuestName: "+guest.getName());
		System.out.println("Total Bill: "+totalCost);
	}
	
	public void displayDetails() {
		System.out.println("GuestId: "+guest.getId());
		System.out.println("GuestName: "+guest.getName());
		System.out.println("Room Number: "+room.roomNumber);
		System.out.println("Room price: "+room.basePrice);
	}
}
