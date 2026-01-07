/*Cab Booking / Ride Management System : Key Features (CRUD)
Real Scenario
Users book rides, drivers accept, and fare is calculated.Key Features
Ride booking
Driver assignment
Fare calculation
Ride history
Concepts Used
OOP: User, Driver, Ride
Interface: FareCalculator
Polymorphism: Peak vs normal pricing
Exception Handling: NoDriverAvailableException.*/

//CRUD features
import java.util.*;
public class RideBookingManagementSystem {
    public static void main(String[] args) {
        UserService userService =new UserService();
        DriverService driverService =new DriverService();
        RideService rideService =new RideService();

        //Creating Users, drivers and Ride
        User user =new User("U1", "Deepak");
        userService.createUser(user);
        Driver driver = new Driver("D1", "Abhay");
        driverService.createDriver(driver);
        
        //Booking ride with Peak and normal fare
        Ride ride1 =new Ride("R1", user, "GLA University", "Chaumuha", 2);
        rideService.createRide(ride1);
        RideBooking booking =new RideBooking(new PeakFareCalculator());
        try {
            booking.assignDriver(ride1, driver);
            booking.calculateFare(ride1);
            booking.completeRide(ride1);

            System.out.println("Peak ride booked by- " + ride1.user.name +"\n Driver is- " + ride1.driver.name +
                               "\n Fare is- " + ride1.fare +"\n Status of ride is- " + ride1.status);
        } catch (NoDriverAvailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        Ride ride2 = new Ride("R2", user, "Chaumuha", "Abhay's hostel", 1);
        rideService.createRide(ride2);
        booking.fare = new NormalFareCalculator();
        try {
            booking.assignDriver(ride2, driver);
            booking.calculateFare(ride2);
            booking.completeRide(ride2);

            System.out.println("Normal Ride booked by- " + ride2.user.name+"\n Driver is- " + ride2.driver.name+
            					"\n Fare is- " + ride2.fare+ "\n Status is- " + ride2.status);

        } catch (NoDriverAvailableException e) {
            System.out.println(e.getMessage());
        }

        //CURD features 
        System.out.println();
        System.out.println("CRUD Service are--- ");

        System.out.println();
        System.out.println("All users are- " +userService.getAllUsers());
        System.out.println("All drivers are- " +driverService.getAllDrivers());
        System.out.println("All rides are- " +rideService.getAllRides());

        System.out.println();
        userService.updateUserName("U1", "Deepak Kumar Rajput");
        driverService.updateDriverName("D1", "Abhay Singh");
        rideService.updateRideDistance("R1", 3);

        System.out.println("After updates details are--- ");
        System.out.println("User is- " +userService.getUser("U1"));
        System.out.println("Driver is- " +driverService.getDriver("D1"));
        System.out.println("Ride R1 is- " +rideService.getRide("R1"));

        System.out.println();
        rideService.deleteRide("R2");
        driverService.deleteDriver("D1");

        System.out.println("After deletions details are- ");
        System.out.println("Drivers are- " +driverService.getAllDrivers());
        System.out.println("Rides are- " +rideService.getAllRides());
    }
}

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

//interface to calculate fare using distance of ride 
interface FareCalculation {
    double calculateFare(double distance);
}

class PeakFareCalculator implements FareCalculation {
    public double calculateFare(double distance) {
        return ((distance*5) + 50) * 2;
    }
}

class NormalFareCalculator implements FareCalculation {
    public double calculateFare(double distance) {
        return (distance*5) + 50;
    }
}

//custom exception class to throw exception
class NoDriverAvailableException extends Exception {
    public NoDriverAvailableException(String message) {
        super(message);
    }
}

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

//CRUD features to user, driver and ride
class UserService {
	
    Map<String, User> users = new HashMap<>();
    public void createUser(User user) {
    	users.put(user.id, user);
    }
    
    public User getUser(String id) { 
    	return users.get(id); 
    }
    
    public List<User> getAllUsers() { 
    	return new ArrayList<>(users.values()); 
    }
    
    public void updateUserName(String id, String newName) {
        User user = users.get(id);
        if (user!=null) {
        	user.name = newName;
        }
    }
    
    public void deleteUser(String id) { 
    	users.remove(id); 
    }
}

class DriverService {
	
    Map<String, Driver> drivers = new HashMap<>();
    public void createDriver(Driver driver) { 
    	drivers.put(driver.id, driver); 
    }
    
    public Driver getDriver(String id) { 
    	return drivers.get(id); 
    }
    
    public List<Driver> getAllDrivers() { 
    	return new ArrayList<>(drivers.values()); 
    }
    
    public void updateDriverName(String id, String newName) {
        Driver driver = drivers.get(id);
        if (driver!=null) {
        	driver.name = newName;
        }
    }
    
    public void deleteDriver(String id) { 
    	drivers.remove(id); 
    }
}

class RideService {
	
    Map<String, Ride> rides = new HashMap<>();
    public void createRide(Ride ride) { 
    	rides.put(ride.id, ride); 
    }
    
    public Ride getRide(String id) { 
    	return rides.get(id); 
    }
    
    public List<Ride> getAllRides() { 
    	return new ArrayList<>(rides.values()); 
    }
    
    public void updateRideDistance(String id, double newDistance) {
        Ride ride = rides.get(id);
        if (ride!=null) {
        	ride.distance = newDistance;
        }
    }
    
    public void deleteRide(String id) { 
    	rides.remove(id); 
    }
}