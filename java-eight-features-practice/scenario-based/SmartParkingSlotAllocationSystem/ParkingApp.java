package SmartParkingSlotAllocationSystem;

public class ParkingApp {
	public static void main(String[] args) {
        ParkingManager manager = new ParkingManager(3);

        try {
            manager.allocateSlot(new Car("CAR01"));
            manager.allocateSlot(new Bike("BIKE02"));
            manager.allocateSlot(new Car("CAR03"));
            manager.allocateSlot(new Bike("BIKE04"));
        } catch (NoParkingSlotAvailableException e) {
            System.out.println(e.getMessage());
        }

        manager.displaySlots();

        manager.freeSlot(2);
        manager.displaySlots();
    }
}
