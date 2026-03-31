package HotelReservationSystem;

public class PeakSeasonPricing implements PricingStrategy{
	@Override
	public double calculate(double basePrice) {
		return basePrice*2;
	}
}
