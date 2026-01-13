package ECommerceOrderManagement;

//Custom Exception for Payment Failures
class PaymentFailedException extends Exception {
 public PaymentFailedException(String message) {
     super(message);
 }
}
