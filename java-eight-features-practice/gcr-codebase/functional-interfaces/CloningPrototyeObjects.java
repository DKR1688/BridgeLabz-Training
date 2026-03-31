
public class CloningPrototyeObjects {
	public static void main(String[] args) {
		try {
			Flight predefinedFlight =new Flight("FL-1234", "SpiceJet");
			System.out.println("Original filght- "+predefinedFlight);

			Flight clonedFlight =(Flight) predefinedFlight.clone();
			System.out.println("Cloned flight- " +clonedFlight);

			//verifying both are separate objects
			System.out.println("Are they same reference? " +(predefinedFlight==clonedFlight));

		} catch (CloneNotSupportedException e) {
			System.out.println("Cloning not supported- "+e.getMessage());
		}
	}
}

//prototype class will be implement cloneable class
class Flight implements Cloneable {
	String flightNumber;
	String flightName;

	Flight(String flightNumber, String flightName) {
		this.flightNumber = flightNumber;
		this.flightName = flightName;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getFlightName() {
		return flightName;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Flight [Number=" + flightNumber + ", Name=" + flightName + "]";
	}
}
