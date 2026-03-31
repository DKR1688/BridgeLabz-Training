package TrafficManager;

public class NodeVehicle {
	String vehicleNumber;
    NodeVehicle next;

    NodeVehicle(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        this.next = null;
    }
}
