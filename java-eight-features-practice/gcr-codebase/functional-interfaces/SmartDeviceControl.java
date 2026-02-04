
public class SmartDeviceControl {
	public static void main(String[] args) {
		SmartDevice light=new Light();
		SmartDevice ac=new AC();
		SmartDevice tv=new TV();

		light.turnOn();
		ac.turnOn();
		tv.turnOn();

		light.turnOff();
		ac.turnOff();
		tv.turnOff();
	}
}

interface SmartDevice {
	void turnOn();

	void turnOff();
}

class Light implements SmartDevice {
	@Override
	public void turnOn() {
		System.out.println("Light is ON");
	}

	@Override
	public void turnOff() {
		System.out.println("Light is OFF");
	}
}

class AC implements SmartDevice {
	@Override
	public void turnOn() {
		System.out.println("AC is ON");
	}

	@Override
	public void turnOff() {
		System.out.println("AC is OFF");
	}
}

class TV implements SmartDevice {
	@Override
	public void turnOn() {
		System.out.println("TV is ON");
	}

	@Override
	public void turnOff() {
		System.out.println("TV is OFF");
	}
}