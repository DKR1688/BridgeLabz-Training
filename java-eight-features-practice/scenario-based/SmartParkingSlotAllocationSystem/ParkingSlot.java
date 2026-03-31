package SmartParkingSlotAllocationSystem;

public class ParkingSlot {
	private int id;
	private boolean isFree;
	private Vehicle vehicle;

	public ParkingSlot(int id) {
		this.id = id;
		this.isFree = true;
	}

	public int getId() {
		return id;
	}

	public boolean isFree() {
		return isFree;
	}

	public void assignVehicle(Vehicle v) {
		this.vehicle = v;
		this.isFree = false;
	}

	public void freeSlot() {
		this.vehicle = null;
		this.isFree = true;
	}

	@Override
	public String toString() {
		return "Slot " + id + (isFree ? " is free" : " occupied by " + vehicle.getNumber());
	}
}
