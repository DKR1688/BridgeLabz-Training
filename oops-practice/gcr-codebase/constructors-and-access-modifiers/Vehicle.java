public class Vehicle {
	//instance variables
    String ownerName;
    String vehicleType;

    //class variable
    static int registrationFee = 1000;

    Vehicle(String ownerName, String vehicleType) {
        this.ownerName =ownerName;
        this.vehicleType =vehicleType;
    }

    //instance method to display vehicle details
    void displayVehicleDetails() {
        System.out.println("Owner name is- " +ownerName +"\n Vehicle type is- " +vehicleType +
                           "\n Registration fee is- "+registrationFee);
        System.out.println();
    }

    //class method to update registration fee
    static void updateRegistrationFee(int newFee) {
        registrationFee =newFee;
        System.out.println("Updated registration fee is- " +registrationFee);
        System.out.println();
    }
    
    public static void main(String[] args) {
        Vehicle vehicle1 =new Vehicle("Deepak Kumar", "Car");
        vehicle1.displayVehicleDetails();
        
        Vehicle vehicle2 =new Vehicle("Amit Sharma", "Bike");
        vehicle2.displayVehicleDetails();

        Vehicle.updateRegistrationFee(2000);

        // Displaying updating
        System.out.println("\nAfter updating registration fee:");
        vehicle1.displayVehicleDetails();
        vehicle2.displayVehicleDetails();
    }
}
