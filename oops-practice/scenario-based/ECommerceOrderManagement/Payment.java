package ECommerceOrderManagement;

interface Payment {
    void pay(double amount) throws PaymentFailedException;
}
