package RideBookingManagementSystem;
//driver class to user to check driver is available or not
class Driver {
  String id;
  String name;
  boolean available;

  Driver(String id, String name) {
      this.id =id;
      this.name =name;
      this.available =true;
  }

  public String toString() {
      return "Driver{id is- '" +id+ "', name is- '" +name+ "', availability- " +available + "}";
  }
}