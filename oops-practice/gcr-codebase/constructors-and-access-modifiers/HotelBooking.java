public class HotelBooking {
	String guestName;
    String roomType;
    int nights;
    
    //Default Constructor
    HotelBooking() {
        this.guestName ="Unknown";
        this.roomType ="Standard";
        this.nights =2;
    }

    //Parameterized Constructor
    HotelBooking(String guestName, String roomType, int nights) {
        this.guestName =guestName;
        this.roomType =roomType;
        this.nights =nights;
    }

    //Copy Constructor
    HotelBooking(HotelBooking other) {
        this.guestName =other.guestName;
        this.roomType =other.roomType;
        this.nights =other.nights;
    }

    void displayBooking() {
        System.out.println("Guest name is- " +guestName+"\n Room type is- " +roomType+
                           "\n Nights to stay are- " + nights);
        System.out.println();
    }

    public static void main(String[] args) {
        //Using Default Constructor
        HotelBooking booking1 =new HotelBooking();
        booking1.displayBooking();

        //Using Parameterized Constructor
        HotelBooking booking2 =new HotelBooking("Deepak Rajput", "Deluxe", 3);
        booking2.displayBooking();

        //Using Copy Constructor
        HotelBooking booking3 =new HotelBooking(booking2);
        booking3.displayBooking();
    }

}
