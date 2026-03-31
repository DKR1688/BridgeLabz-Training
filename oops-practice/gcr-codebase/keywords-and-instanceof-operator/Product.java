//Shopping Cart System
class Product {
    //static variable shared by all products
    static double discount = 0.0;
    
    //static method to update discount
    public static void updateDiscount(double updatedDiscount) {
        discount =updatedDiscount;
    }

    final String productID;
    String productName;
    double price;
    int quantity;

    public Product(String productID, String productName, double price, int quantity) {
        this.productID =productID;
        this.productName =productName;
        this.price =price;
        this.quantity =quantity;
    }

    //this method is to calculate price after discount
    double getPriceAfterDiscount() {
        return price - (price*discount/100);
    }

    void displayProductDetails(Object object) {
        if (object instanceof Product) {
            System.out.println("Product ID is- " +productID);
            System.out.println("Product name is- " +productName);
            System.out.println("Price is- $" +price);
            System.out.println("Quantity is- " +quantity);
            System.out.println("Discount is- " + discount + "%");
            System.out.println("Price after discount is- $" +getPriceAfterDiscount());
            System.out.println();
        } else {
            System.out.println("Not a valid Product object.");
        }
    }
    
    public static void main(String[] args) {
        Product product1 =new Product("P001", "Laptop", 1200.0, 5);
        Product product2 =new Product("P002", "Smartphone", 800.0, 10);
        
        Product.updateDiscount(10.0);
        
        //displaying product details using instanceof
        product1.displayProductDetails(product1);
        product2.displayProductDetails(product2);
    }
}