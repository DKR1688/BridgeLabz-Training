package RideBookingManagementSystem;
class NormalFareCalculator implements FareCalculation {
    public double calculateFare(double distance) {
        return (distance*5) + 50;
    }
}
