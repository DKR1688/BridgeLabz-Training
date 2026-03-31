package HotelReservationSystem;

public abstract class Room {
	protected int roomNumber;
	protected double basePrice;
	protected boolean isAvailable = true;
	
	public Room(int roomNumber,double basePrice) {
		this.roomNumber = roomNumber;
		this.basePrice = basePrice;
	}
	
	public void book() {
		isAvailable = false;
	}
	
	public void checkout() {
		isAvailable = true;
	}
	
	public abstract double calculatePrice(PricingStrategy strategy);
}
