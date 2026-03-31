import java.util.*;
public class SensitiveDataTagging {
	public static void main(String[] args) {
		CustomerInfo customer=new CustomerInfo("Deepak", "1234567898765432");
		FlightInfo flight =new FlightInfo("FL-1234", "Delhi");

		//checking encryption requirement
		EncryptionUtil.encryptIfSensitive(customer);
		EncryptionUtil.encryptIfSensitive(flight);
	}
}

interface SensitiveData {
	//this is custom marker interface
}
//this is sensitive class
class CustomerInfo implements SensitiveData {
	String name;
	String creditCardNumber;

	CustomerInfo(String name, String creditCardNumber) {
		this.name =name;
		this.creditCardNumber =creditCardNumber;
	}

	public String getName() {
		return name;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}
}

//this is non-sensitive class
class FlightInfo {
	String flightNumber;
	String destination;

	FlightInfo(String flightNumber, String destination) {
		this.flightNumber =flightNumber;
		this.destination =destination;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getDestination() {
		return destination;
	}
}

//this is to check and encrypt sensitive data
class EncryptionUtil {
	public static void encryptIfSensitive(Object obj) {
		if (obj instanceof SensitiveData) {
			System.out.println("Encrypting needed for- "+obj.getClass().getSimpleName());
		} else {
			System.out.println("No encryption needed for- "+obj.getClass().getSimpleName());
		}
	}
}
