package HotelReservationSystem;

public class StandardRoom extends Room{
	public StandardRoom(int roomNumber) {
		super(roomNumber,3000);
	}
	
	@Override
	public double calculatePrice(PricingStrategy strategy) {
		return strategy.calculate(basePrice);
	}
}
