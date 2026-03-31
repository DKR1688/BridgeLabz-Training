public class SmartHomeDevices {
	public static void main(String[] args) {
	     Thermostat thermostat = new Thermostat("01", "ON", 10);
	     thermostat.displayStatus();
	 }
}

//Superclass
class Device {
 String deviceId;
 String status;

 public Device(String deviceId, String status) {
     this.deviceId =deviceId;
     this.status = status;
 }

 public void displayStatus() {
     System.out.println("Device ID is- " +deviceId+ ",\n Status is- " +status);
 }
}

class Thermostat extends Device {
	int temperatureSetting;

 	public Thermostat(String deviceId, String status, int temperatureSetting) {
 		super(deviceId, status);
 		this.temperatureSetting = temperatureSetting;
 	}

 	@Override
 	public void displayStatus() {
 		super.displayStatus();
 		System.out.println("Temperature setting is- " + temperatureSetting + "°C");
 	}
}