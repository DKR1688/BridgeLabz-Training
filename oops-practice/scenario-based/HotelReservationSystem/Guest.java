package HotelReservationSystem;

public class Guest {
	private int guestId;
	private String guestName;
	public Guest(int guestId,String guestName) {
		this.guestId = guestId;
		this.guestName = guestName;
	}
	
	public int getId() {
		return guestId;
	}
	
	public String getName() {
		return guestName;
	}
}
