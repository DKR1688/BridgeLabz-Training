package ECommerceOrderManagement;

//Card Payment Implementation
class CardPayment implements Payment {
 @Override
 public void pay(double amount) throws PaymentFailedException {
     if (amount <= 0) {
    	 throw new PaymentFailedException("Invalid amount for Card Payment!");
     }
     System.out.println("Paid " + amount +" rs using Card.");
 }
}

