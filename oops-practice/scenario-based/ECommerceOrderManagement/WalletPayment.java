package ECommerceOrderManagement;

//Wallet Payment Implementation
class WalletPayment implements Payment {
 @Override
 public void pay(double amount) throws PaymentFailedException {
     if (amount <= 0) {
    	 throw new PaymentFailedException("Invalid amount for Wallet Payment!");
     }
     System.out.println("Paid " + amount+ " rs using Wallet.");
 }
}

