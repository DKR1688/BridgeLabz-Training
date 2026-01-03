
public class VehicleManagementSystem {
	public static void main(String[] args) {
		ElectricVehicle electric = new ElectricVehicle("Tesla model", 200);
		PetrolVehicle petrol = new PetrolVehicle("Hero honda", 180);

		electric.charge();
		petrol.refuel();
	}
}

class Vehicle2 {
	String model;
	int maxSpeed;

	public Vehicle2(String model, int maxSpeed) {
		this.model = model;
		this.maxSpeed = maxSpeed;
	}
}

//Interface
interface Refuelable {
	void refuel();
}

class ElectricVehicle extends Vehicle2 {
	public ElectricVehicle(String model, int maxSpeed) {
		super(model, maxSpeed);
	}

	public void charge() {
		System.out.println("Electric Vehicle model is- " + model);
	}
}

class PetrolVehicle extends Vehicle2 implements Refuelable {
	public PetrolVehicle(String model, int maxSpeed) {
		super(model, maxSpeed);
	}

	@Override
	public void refuel() {
		System.out.println("Petrol Vehicle model is- " + model);
	}
}
