package RideBookingManagementSystem;

//class to book a ride threw user or driver
class RideBooking {
  FareCalculation fare;

  RideBooking(FareCalculation fare) {
      this.fare =fare;
  }

  public void assignDriver(Ride ride, Driver driver) throws NoDriverAvailableException {
      if (!driver.available) {
          throw new NoDriverAvailableException("Sorry, driver is unavailable");
      }
      ride.driver = driver;
      driver.available = false;
      ride.status = "Driver busy";
  }

  public void calculateFare(Ride ride) {
      ride.fare = fare.calculateFare(ride.distance);
  }

  public void completeRide(Ride ride) {
      ride.status = "Ride completed";
      ride.driver.available = true;
  }
}