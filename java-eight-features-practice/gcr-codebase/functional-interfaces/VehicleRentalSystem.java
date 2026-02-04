
public class VehicleRentalSystem {
	public static void main(String[] args) {
		VehicleRental car = new Car();
		VehicleRental bike = new Bike();
		VehicleRental bus = new Bus();

		car.rent();
		bike.rent();
		bus.rent();

		car.returnVehicle();
		bike.returnVehicle();
		bus.returnVehicle();
	}

}

interface VehicleRental {
	void rent();

	void returnVehicle();
}

class Car implements VehicleRental {
	@Override
	public void rent() {
		System.out.println("Car has been rented.");
	}

	@Override
	public void returnVehicle() {
		System.out.println("Car has been returned.");
	}
}

class Bike implements VehicleRental {
	@Override
	public void rent() {
		System.out.println("Bike has been rented.");
	}

	@Override
	public void returnVehicle() {
		System.out.println("Bike has been returned.");
	}
}

class Bus implements VehicleRental {
	@Override
	public void rent() {
		System.out.println("Bus has been rented.");
	}

	@Override
	public void returnVehicle() {
		System.out.println("Bus has been returned.");
	}
}