package RideBookingManagementSystem;

import java.util.*;

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

