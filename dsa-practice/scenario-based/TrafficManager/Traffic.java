package TrafficManager;

public class Traffic {
	NodeVehicle head = null;

    void addToRoundabout(String vehicle) {
        NodeVehicle newNode = new NodeVehicle(vehicle);

        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            NodeVehicle temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
        System.out.println(vehicle + " entered the roundabout");
    }

    void removeFromRoundabout(String vehicle) {
        if (head == null) {
            System.out.println("Roundabout is empty");
            return;
        }

        NodeVehicle curr = head, prev = null;

        do {
            if (curr.vehicleNumber.equals(vehicle)) {
                if (curr == head) {
                    NodeVehicle last = head;
                    while (last.next != head) {
                        last = last.next;
                    }
                    head = head.next;
                    last.next = head;
                } else {
                    prev.next = curr.next;
                }
                System.out.println(vehicle + " exited the roundabout");
                return;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != head);

        System.out.println("Vehicle not found in roundabout");
    }

    void printRoundabout() {
        if (head == null) {
            System.out.println("Roundabout is empty");
            return;
        }

        System.out.print("Roundabout Vehicles: ");
        NodeVehicle temp = head;
        do {
            System.out.print(temp.vehicleNumber + " → ");
            temp = temp.next;
        } while (temp != head);
        System.out.println("(back to start)");
    }
}
