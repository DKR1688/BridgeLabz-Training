package RideBookingManagementSystem;

//ride class to user and driver
class Ride {
  String id;
  String pickup;
  String drop;
  double distance;
  double fare;
  String status;

  User user;
  Driver driver;

  Ride(String id, User user, String pickup, String drop, double distance) {
      this.id =id;
      this.user =user;
      this.pickup =pickup;
      this.drop =drop;
      this.distance =distance;
      this.status ="REQUESTED";
  }

  public String toString() {
      return "Ride{id is- '" +id+ "', user is- " +user.name+", driver is- " +(driver != null ? driver.name : "none") +
             ", pickup from- '" +pickup+ "', droped palce is- '" +drop+ "', distance is- " + distance + ", fare=" + fare +
             ", status of ride- " +status + "}";
  }
}
