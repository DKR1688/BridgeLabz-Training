
public class DigitalPayment {
	public static void main(String[] args) {
		payment up = new UPI();
		up.pay();
		payment p = new Debitcard();
		p.pay();
		payment db = new CreditCard();
		db.pay();
	}
}

interface payment {
	void pay();
}

class UPI implements payment {
	public void pay() {
		System.out.println("UPI Payment Successfully");

	}
}

class wallet implements payment {
	public void pay() {
		System.out.println("E-Wallet Done");

	}

}

class CreditCard implements payment {
	public void pay() {
		System.out.println("CeditCard Payment Done");

	}
}

class Debitcard implements payment {
	public void pay() {
		System.out.println("DebitCard payment Successfull");

	}
}
