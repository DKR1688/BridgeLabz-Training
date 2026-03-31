package RestaurrantReservationSystem;

public class RestaurantApp {
	public static void main(String[] args) {
        Restaurant restaurant=new Restaurant();

        restaurant.addTable(new Table(1, 2));
        restaurant.addTable(new Table(2, 4));
        restaurant.addTable(new Table(3, 6));
        try {
            restaurant.reserveTable("Deepak", 1, "7PM-9PM");
            restaurant.reserveTable("Abhay", 2, "7PM-9PM");

            //we are trying double booking
            restaurant.reserveTable("Rajput", 1, "7PM-9PM");

        }catch (TableAlreadyReservedException e) {
            System.out.println("Error- "+e.getMessage());
        }
        
        System.out.println();
        restaurant.showAvailableTables("7PM-9PM");
        System.out.println();
        restaurant.cancelReservation(1, "7PM-9PM");
        System.out.println();
        restaurant.showAvailableTables("7PM-9PM");
    }
}
