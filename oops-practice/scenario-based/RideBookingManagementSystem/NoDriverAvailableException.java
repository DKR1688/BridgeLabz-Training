package RideBookingManagementSystem;

//custom exception class to throw exception
class NoDriverAvailableException extends Exception {
  public NoDriverAvailableException(String message) {
      super(message);
  }
}