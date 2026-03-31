package TrafficManager;

//vehicle class represents each car in the roundabout
class Vehicle {
	String number;
	Vehicle next; //pointer to next vehicle

	Vehicle(String number) {
		this.number =number;
		this.next =null;
	}
}
