import java.util.*;

class Product {
    String name;
    double price;
    double rating;
    double discount;

    Product(String name, double price, double rating, double discount) {
        this.name =name;
        this.price =price;
        this.rating =rating;
        this.discount =discount;
    }

    @Override
    public String toString() {
        return name + " | Price- " + price +
               " | Rating- " + rating +
               " | Discount- " + discount + "%";
    }
}

public class CustomEcommerceSorting {
    public static void main(String[] args) {

        List<Product> products = Arrays.asList(
                new Product("Laptop", 70000, 4.5, 10),
                new Product("Phone", 30000, 4.7, 15),
                new Product("Headphones", 2000, 4.2, 25),
                new Product("Smartwatch", 15000, 4.3, 20)
        );

        //sorting by Price 
        products.sort((p1, p2) -> Double.compare(p1.price, p2.price));
        System.out.println(" Sorted by price-");
        products.forEach(System.out::println);

        //Sorting by RatingHigh to Low
        products.sort((p1, p2) -> Double.compare(p2.rating, p1.rating));
        System.out.println("\n Sorted by rating-");
        products.forEach(System.out::println);

        //sorting by Discount
        products.sort((p1, p2) -> Double.compare(p2.discount, p1.discount));
        System.out.println("\n Sorted by discount-");
        products.forEach(System.out::println);
    }
}
