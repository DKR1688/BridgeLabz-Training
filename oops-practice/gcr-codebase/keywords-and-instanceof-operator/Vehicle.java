//Vehicle Registration System
class Vehicle {
    //A static variable registrationFee common for all vehicles.
    static double registrationFee =0.0;
    
    //Use a final variable registrationNumber to uniquely identify each vehicle.
    final String registrationNumber;
    String ownerName;
    String vehicleType;

    //Use this to initialize ownerName, vehicleType, and registrationNumber in the constructor.
    Vehicle(String registrationNumber, String ownerName, String vehicleType) {
        this.registrationNumber =registrationNumber;
        this.ownerName =ownerName;
        this.vehicleType =vehicleType;
    }

    //A static method updateRegistrationFee() to modify the fee.
    static void updateRegistrationFee(double updatedFee) {
        registrationFee =updatedFee;
    }

    //Check if an object belongs to the Vehicle class before displaying its registration
    void displayVehicleDetails(Object object) {
        if (object instanceof Vehicle) {
            System.out.println("Owner Name is- " +ownerName);
            System.out.println("Vehicle Type is- " +vehicleType);
            System.out.println("Registration Number is- " +registrationNumber);
            System.out.println("Registration Fee is- $" + registrationFee);
            System.out.println();
        } else {
            System.out.println("Not a valid Vehicle object.");
        }
    }
    
    public static void main(String[] args) {
        Vehicle vehicle1 = new Vehicle("ABC123", "Honest raj", "Sedan");
        Vehicle vehicle2 = new Vehicle("XYZ789", "Price danish", "SUV");

        Vehicle.updateRegistrationFee(150.0);
        
        //displaying vehicle details using instanceof
        vehicle1.displayVehicleDetails(vehicle1);
        vehicle2.displayVehicleDetails(vehicle2);
    }
}
