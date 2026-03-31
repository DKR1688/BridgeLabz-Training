package RideBookingManagementSystem;

import java.util.*;
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