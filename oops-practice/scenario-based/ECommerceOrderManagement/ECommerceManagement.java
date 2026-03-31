package ECommerceOrderManagement;

public class ECommerceManagement{
    public static void main(String[] args) {
        //create Products
        Product p1 =new Product(1, "Laptop", 56000);
        Product p2 =new Product(2, "Smartphone", 12000);

        //create Customer
        Customer c1 =new Customer(111, "Deepak");

        // Place Order
        Order order1 =new Order(121, c1, p2);
        System.out.println("Order placed- " + order1.product);

        //payment processing with polymorphism
        Payment paymentMethod =new UpiPayment();
        
        try {
            paymentMethod.pay(order1.product.price);
        } 
        catch (PaymentFailedException e) {
            System.out.println("Payment failed- " + e.getMessage());
        }

        //tracking Order
        order1.trackOrder();

        //checking for cancel Order
        order1.cancelOrder();
        order1.trackOrder();
    }
}
