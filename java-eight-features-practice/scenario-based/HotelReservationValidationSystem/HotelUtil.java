package HotelReservationValidationSystem;
import java.util.*;
public class HotelUtil {
	
	
	public boolean validateRoomNumber(String roomNumber) throws InvalidReservationException {
		if(roomNumber.matches("RM-[0-9]{3}")) {
			int number=Integer.parseInt(roomNumber.substring(3));
			if (number< 100 || number> 999) {
				throw new InvalidReservationException("The room number " + roomNumber + " is invalid");
			}
		}
		return true;
	}
	
	public boolean validateHotelName(String hotelName) throws InvalidReservationException{
		List<String> name = Arrays.asList("Taj", "Marriott", "Hyatt", "Hilton");
		if(!name.contains(hotelName)) {
			throw new InvalidReservationException("THe hotel name is invalid");		}
		return true;
	}
	
	public boolean validateGuestCount(int guestCount, String hotelName) throws InvalidReservationException  {
		Map<String, Integer> map=new HashMap<>();
		map.put("Taj", 4);
		map.put("Marriott", 5);
		map.put("Hyatt", 3);
		map.put("Hilton", 2);
		
		if(guestCount<=0 || guestCount>map.get(hotelName)) {
			throw new InvalidReservationException("The guest count " + guestCount + " is invalid for " + hotelName);
		}
		
		return true;
	}
	
	public int calculateRoomsRequired(String hotelName, int guestCount) throws InvalidReservationException{
		Map<String, Integer> map=new HashMap<>();
		map.put("Taj", 4);
		map.put("Marriott", 5);
		map.put("Hyatt", 3);
		map.put("Hilton", 2);
		
		int roomCapacity=map.get(hotelName);
		int  rooms = (int)Math.ceil((double)guestCount / roomCapacity);
		
		if(guestCount<=0) {
			throw new InvalidReservationException("The guest count is invalid for "+hotelName);
		}
		
		return rooms;
	}

}
