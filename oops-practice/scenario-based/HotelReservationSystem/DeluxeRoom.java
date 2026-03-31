package HotelReservationSystem;

public class DeluxeRoom extends Room{
	public DeluxeRoom(int roomNumber) {
		super(roomNumber,4000);
	}
	
	@Override
	public double calculatePrice(PricingStrategy strategy) {
		return strategy.calculate(basePrice);
	}
}
