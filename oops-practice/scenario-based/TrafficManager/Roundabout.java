package TrafficManager;

//circular linked list for roundabout
class Roundabout {
	private Vehicle head =null;

	public void addVehicle(String number) {
		Vehicle newCar =new Vehicle(number);
		if (head == null) {
			head = newCar;
			head.next =head;
		} else {
			Vehicle temp = head;
			while (temp.next !=head) {
				temp =temp.next;
			}
			temp.next =newCar;
			newCar.next = head;
		}
		System.out.println("Car number is- " +number+ " entered roundabout.");
	}

	public void removeVehicle(String number) {
		if (head ==null) {
			System.out.println("Roundabout is empty.");
			return;
		}

		Vehicle curr = head;
		Vehicle prev = null;
		do {
			if (curr.number.equals(number)) {
				if (prev ==null) {
					if (head.next == head) {
						head =null;
					} else {
						Vehicle temp =head;
						while (temp.next !=head) {
							temp = temp.next;
						}
						temp.next =head.next;
						head =head.next;
					}
				} else {
					prev.next =curr.next;
				}
				System.out.println("Car number is- " + number+" exited roundabout.");
				return;
			}
			prev =curr;
			curr =curr.next;
		} while (curr != head);
		System.out.println("Car number is- " + number+" not found in roundabout.");
	}

	//printing current state of roundabout
	public void printRoundabout() {
		if (head == null) {
			System.out.println("Roundabout is empty.");
			return;
		}
		System.out.print("Roundabout- ");
		Vehicle temp =head;
		do {
			System.out.print(temp.number + " is- ");
			temp =temp.next;
		} while (temp != head);
		System.out.println("Back to " + head.number);
	}
}
