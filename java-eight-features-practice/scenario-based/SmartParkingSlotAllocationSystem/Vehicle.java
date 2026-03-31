package SmartParkingSlotAllocationSystem;

public abstract class Vehicle {
    private String number;

    public Vehicle(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}

class Car extends Vehicle {
    public Car(String number) {
        super(number);
    }
}

class Bike extends Vehicle {
    public Bike(String number) {
        super(number);
    }
}
