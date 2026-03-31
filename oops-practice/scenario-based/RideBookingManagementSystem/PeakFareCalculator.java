package RideBookingManagementSystem;

class PeakFareCalculator implements FareCalculation {
    public double calculateFare(double distance) {
        return ((distance*5) + 50) * 2;
    }
}