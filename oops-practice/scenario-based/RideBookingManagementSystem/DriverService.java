package RideBookingManagementSystem;

import java.util.*;
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