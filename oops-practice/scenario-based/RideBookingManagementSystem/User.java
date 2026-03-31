package RideBookingManagementSystem;
//user class with id and name to book a ride 
class User {
  String id;
  String name;

  User(String id, String name) {
      this.id =id;
      this.name =name;
  }

  public String toString() {
      return "User{id is- '" +id+ "', name is- '" +name+ "'}";
  }
}