package HotelReservationValidationSystem;
import java.util.*;
public class UserInterface {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		HotelUtil util=new HotelUtil();
		
		System.out.println("Enter room details sepraed by (:)- ");
		String in=sc.nextLine();
		try {
			String[] parts=in.split(":");
			String roomNumber=parts[0];
			String hotelName=parts[1];
			int guestCount=Integer.parseInt(parts[2]);
			
			util.validateRoomNumber(roomNumber);
			util.validateHotelName(hotelName);
			System.out.println("Room required: "+util.calculateRoomsRequired(hotelName, guestCount));

			util.validateGuestCount(guestCount, hotelName);
			
		}catch (InvalidReservationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input format. Please enter details as <RoomNumber>:<HotelName>:<GuestCount>");
        } finally {
            sc.close();
        }
	}
}
