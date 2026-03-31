package TrafficManager;

public class Manager {
	public static void main(String[] args) {
        Roundabout roundabout = new Roundabout();
        VehicleQueue queue = new VehicleQueue(3);

        //cars arrive
        queue.enqueue("A1");
        queue.enqueue("B2");
        queue.enqueue("C3");
        queue.enqueue("D4");

        //cars enter from queue to roundabout
        while (!queue.isEmpty()) {
            String car =queue.dequeue();
            roundabout.addVehicle(car);
        }
        roundabout.printRoundabout();

        //cars exit or not
        roundabout.removeVehicle("B2");
        roundabout.printRoundabout();

        //we will try removing non-existing car
        roundabout.removeVehicle("X9");
    }
}
