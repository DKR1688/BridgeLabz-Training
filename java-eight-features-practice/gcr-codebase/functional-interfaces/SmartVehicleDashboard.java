
public class SmartVehicleDashboard {
	public static void main(String[] args) {
        Vehicle petrolCar=new PetrolCar();
        Vehicle electricCar=new ElectricCar();

        petrolCar.displaySpeed(80);
        electricCar.displaySpeed(100);
        electricCar.displayBattery(85);
    }
}

interface Vehicle {
    void displaySpeed(int speed);

    //adding default method for battery percentage
    default void displayBattery(int batteryPercentage) {
        System.out.println("Battery Percentage- "+batteryPercentage+ "%");
    }
}

class PetrolCar implements Vehicle {
    @Override
    public void displaySpeed(int speed) {
        System.out.println("Petrol car speed- "+speed+" km/h");
    }
    //no need to implement displayBattery because petrol cars do not use battery percentage
}

class ElectricCar implements Vehicle {
    @Override
    public void displaySpeed(int speed) {
        System.out.println("Electric car speed- "+speed+" km/h");
    }

    @Override
    public void displayBattery(int batteryPercentage) {
        System.out.println("Electric car battery- "+batteryPercentage + "%");
    }
}

