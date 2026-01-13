package RideBookingManagementSystem;
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
public class RideBookingSystem {
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