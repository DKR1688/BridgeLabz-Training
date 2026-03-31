package AeroVigil;
import java.util.*;
public class UserInterface {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter flight details- ");
        String in =sc.nextLine();
        
        String[] details= in.split(":");
        String flightNumber =details[0];
        String flightName =details[1];
        
        int passengerCount =Integer.parseInt(details[2]);
        double currentFuelLevel =Double.parseDouble(details[3]);

        FlightUtil util=new FlightUtil();
        try {
            if (util.validateFlightNumber(flightNumber) && util.validateFlightName(flightName) &&
                util.validatePassengerCount(passengerCount, flightName)) {

                double fuelRequired =util.calculateFuelToFillTank(flightName, currentFuelLevel);
                System.out.println("Fuel required to fill the tank- "+fuelRequired+" liters");
            }
        } catch (InvalidFlightException e) {
            System.out.println(e.getMessage());
        }
    }
}
