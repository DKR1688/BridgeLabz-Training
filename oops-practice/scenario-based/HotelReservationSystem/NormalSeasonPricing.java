package HotelReservationSystem;

public class NormalSeasonPricing implements PricingStrategy{
	@Override
	public double calculate(double basePrice) {
		return basePrice;
	}
}
