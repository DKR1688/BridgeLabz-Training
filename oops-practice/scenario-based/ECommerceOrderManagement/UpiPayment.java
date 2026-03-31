package ECommerceOrderManagement;

//UPI Payment Implementation
class UpiPayment implements Payment {
	@Override
	public void pay(double amount) throws PaymentFailedException {
		if (amount <= 0) {
			throw new PaymentFailedException("Invalid amount for UPI Payment!");
		}
		System.out.println("Paid " + amount+ " rs using UPI.");
	}
}
