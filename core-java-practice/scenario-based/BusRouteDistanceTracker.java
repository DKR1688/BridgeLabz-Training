/*Bus Route Distance Tracker 🚌
Each stop adds distance.
● Ask if the passenger wants to get off at a stop.
● Use a while-loop with a total distance tracker.
● Exit on user confirmation.*/

import java.util.*;
public class BusRouteDistanceTracker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		
        int addedDistance=0;
        while (true) {
    		System.out.println("Add your stop distance ");
    		int eachStopDistance =scanner.nextInt();
        	
            // we will add distance for each stop
            addedDistance += eachStopDistance;
            System.out.println("Total distance traveled "+addedDistance +" km");

            // Asking passenger at each stop if they want to get off
            System.out.print("Do you want to get off here? (yes/no) ");
            String passengerChoice = scanner.next();

            if (passengerChoice.equals("yes")) {
                System.out.println("Passenger got off after reaching his stop at "+addedDistance +" km.");
                break;
            } else if (passengerChoice.equals("no")) {
                System.out.println("Bus will continue to the next stop.");
            } else {
                System.out.println("Please enter 'yes' or 'no' according your choice");
            }
        }
        scanner.close();
	}

}
