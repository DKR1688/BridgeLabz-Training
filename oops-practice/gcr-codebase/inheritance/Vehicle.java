
public class Vehicle {
    int maxSpeed;
    String fuelType;

    Vehicle(int maxSpeed, String fuelType) {
        this.maxSpeed =maxSpeed;
        this.fuelType =fuelType;
    }

    void displayInfo() {
        System.out.println("Vehicle details...");
    }
    
    public static void main(String[] args) {
        //Vehicle reference, subclass objects
        Vehicle[] vehicles = new Vehicle[3];
        vehicles[0] = new Car(200, "Petrol", 10);
        vehicles[1] = new Truck(150, "Diesel", 15);
        vehicles[2] = new Motorcycle(100, "Petrol", "Yes");

        // Dynamic method calls to subclasses
        for (Vehicle v : vehicles) {
            v.displayInfo();
            System.out.println();
        }
    }
}

class Car extends Vehicle {
    int seatCapacity;

    Car(int maxSpeed, String fuelType, int seatCapacity) {
        super(maxSpeed, fuelType);
        this.seatCapacity =seatCapacity;
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("Max speed of car- " +maxSpeed);
        System.out.println("Fuel type of car- " +fuelType);
        System.out.println("Seat capacity of car- " +seatCapacity);
    }
}

class Truck extends Vehicle {
    double loadCapacity;

    Truck(int maxSpeed, String fuelType, double loadCapacity) {
        super(maxSpeed, fuelType);
        this.loadCapacity =loadCapacity;
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("Max speed of truck- " +maxSpeed);
        System.out.println("Fuel type of truck- " +fuelType);
        System.out.println("Load capacity of truck- " +loadCapacity + " tons");
    }
}

class Motorcycle extends Vehicle {
	String isFast;

    Motorcycle(int maxSpeed, String fuelType, String isFast) {
        super(maxSpeed, fuelType);
        this.isFast =isFast;
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("Max speed of bike- " + maxSpeed + " km/h");
        System.out.println("Fuel type of bike- " + fuelType);
        System.out.println("Motorcycle is fast or not- " +isFast);
    }
}