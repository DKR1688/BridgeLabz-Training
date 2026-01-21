import java.util.*;
public class DynamicOnlineMarketplace {
	public static void main(String[] args) {
		//creating products
        Product<BookCategory> book = new Product<>("Java", 500, BookCategory.SCIENCE);
        Product<ClothingCategory> shirt = new Product<>("T-Shirt", 300, ClothingCategory.MENS);
        Product<GadgetCategory> phone = new Product<>("Phone", 10000, GadgetCategory.MOBILE);

        //creating catalogs
        Catalog catalog =new Catalog();
        catalog.addProduct(book);
        catalog.addProduct(shirt);
        catalog.addProduct(phone);

        System.out.println("Before discount- ");
        catalog.showCatalog();
        System.out.println();
        
        //Applying discounts and then displaying
        DiscountUtil.applyDiscount(book, 10);
        DiscountUtil.applyDiscount(shirt, 20);
        DiscountUtil.applyDiscount(phone, 5);
        System.out.println("After discount- ");
        catalog.showCatalog();
    }
}

//here we are defining category enums
enum BookCategory { 
	FICTION, SCIENCE 
}
enum ClothingCategory {
	MENS, WOMENS 
}
enum GadgetCategory { 
	MOBILE, LAPTOP 
}

//a generic class Product<T> where T is restricted to a category 
class Product<T> {
    String name;
    double price;
    T category;

    Product(String name, double price, T category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return name + ", Price- " + price + ", Category- " + category;
    }
}

//a generic method to apply discounts dynamically (<T extends Product> void applyDiscount(T product, double percentage)).
class DiscountUtil {
    public static <T extends Product<?>> void applyDiscount(T product, double percent) {
        product.price -= product.price * (percent/100);
    }
}

//ensure type safety while allowing multiple product categories to exist in the same catalog.
class Catalog {
    List<Product<?>> products = new ArrayList<>();

    void addProduct(Product<?> p) { 
    	products.add(p);
    }
    void showCatalog() {
        for (Product<?> p : products) {
            System.out.println(p);
        }
    }
}
