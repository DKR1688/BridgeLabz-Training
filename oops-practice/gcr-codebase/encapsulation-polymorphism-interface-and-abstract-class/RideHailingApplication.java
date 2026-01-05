
public class RideHailingApplication {
	public static void main(String[] args) {
		RideVehicle v1 = new CarRide("C1", "Deepak", 15);
		RideVehicle v2 = new BikeRide("B2", "Priyank", 8);
		RideVehicle v3 = new AutoRide("A3", "Abahy", 10);

		v1.getVehicleDetails();
		System.out.println("Fare is- " + v1.calculateFare(10));
		System.out.println();

		v2.getVehicleDetails();
		System.out.println("Fare is- " + v2.calculateFare(10));
		System.out.println();

		v3.getVehicleDetails();
		System.out.println("Fare is- " + v3.calculateFare(10));
		System.out.println();
	}
}

//Abstract
abstract class RideVehicle {
	private String vehicleId;
	private String driverName;
	private double ratePerKm;

	public RideVehicle(String vehicleId, String driverName, double ratePerKm) {
		this.vehicleId = vehicleId;
		this.driverName = driverName;
		this.ratePerKm = ratePerKm;
	}

	public void getVehicleDetails() {
		System.out.println("Vehicle is- " + vehicleId + "\n Driver is- " + driverName + "\n Rate/Km is- " + ratePerKm);
	}

	public double getRatePerKm() {
		return ratePerKm;
	}

	public abstract double calculateFare(double distance);
}

//GPS Interface
interface GPS {
	String getCurrentLocation();

	void updateLocation(String newLocation);
}

//Car
class CarRide extends RideVehicle implements GPS {
	private String location;

	public CarRide(String vehicleId, String driverName, double ratePerKm) {
		super(vehicleId, driverName, ratePerKm);
	}

	@Override
	public double calculateFare(double distance) {
		return distance * getRatePerKm();
	}

	@Override
	public String getCurrentLocation() {
		return location;
	}

	@Override
	public void updateLocation(String newLocation) {
		this.location = newLocation;
	}
}

//Bike
class BikeRide extends RideVehicle {
	public BikeRide(String vehicleId, String driverName, double ratePerKm) {
		super(vehicleId, driverName, ratePerKm);
	}

	@Override
	public double calculateFare(double distance) {
		return distance * getRatePerKm();
	}
}

//Auto
class AutoRide extends RideVehicle {
	public AutoRide(String vehicleId, String driverName, double ratePerKm) {
		super(vehicleId, driverName, ratePerKm);
	}

	@Override
	public double calculateFare(double distance) {
		return distance * getRatePerKm() + 20; // base charge
	}
}
