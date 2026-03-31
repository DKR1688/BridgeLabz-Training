public class VehicleRentalSystem {
    public static void main(String[] args) {
        Bike bike = new Bike("B1", "Hero", 300);
        Car car = new Car("C1", "Honda", 1000);
        Truck truck = new Truck("T1", "Tata", 2000);
        
        bike.displayInfo();
        car.displayInfo();
        truck.displayInfo();

        //customer rents vehicles
        Customer cust = new Customer("CU1", "Deepak");
        cust.rent(bike, 3);
        cust.rent(car, 5);
        cust.rent(truck, 2);

        car.updateRate(1200);

        truck.delete();
    }
}

//interface for rent calculation
interface IRentable {
    double calculateRent(int days);
}

abstract class Vehicle implements IRentable {
    protected String id;
    protected String brand;
    protected double dailyRate;

    public Vehicle(String id, String brand, double dailyRate) {
        this.id =id;
        this.brand =brand;
        this.dailyRate =dailyRate;
    }

    //reading or displaying all info
    public void displayInfo() {
        System.out.println("ID is- " + id + "\n Brand is- " + brand + "\n Rate- " + dailyRate);
    }

    //Updating rate of vehicle
    public void updateRate(double newRate) {
        this.dailyRate = newRate;
        System.out.println("Rate updated to " + newRate);
    }

    // Deleting vehicle
    public void delete() {
        System.out.println("Vehicle " + id + " deleted.");
    }
}

//Bike class
class Bike extends Vehicle {
    public Bike(String id, String brand, double dailyRate) {
        super(id, brand, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        return dailyRate * days;
    }
}

// Car class
class Car extends Vehicle {
    public Car(String id, String brand, double dailyRate) {
        super(id, brand, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        return (dailyRate * days)+ 500; 
    }
}

// Truck class
class Truck extends Vehicle {
    public Truck(String id, String brand, double dailyRate) {
        super(id, brand, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        return (dailyRate * days)+ 1000;
    }
}

// Customer class
class Customer {
    protected String id;
    protected String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void rent(Vehicle v, int days) {
        double cost = v.calculateRent(days);
        System.out.println(name + " rented " + v.brand + " for " + days + " days. Cost is- " + cost);
    }
}