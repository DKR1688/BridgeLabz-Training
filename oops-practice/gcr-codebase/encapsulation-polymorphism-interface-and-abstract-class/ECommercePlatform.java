public class ECommercePlatform {
	public static void printFinalPrices(Product product) {
		double price = product.getPrice();
		double discount = product.calculateDiscount();
		double tax = 0.0;
		
		if (product instanceof Taxable) {
			Taxable t = (Taxable) product;
			tax = t.calculateTax();
		}
		double finalPrice = price + tax - discount;
		
		System.out.println("Product is- " +product.toString());
		System.out.println("Base Price is- " +price);
		System.out.println("Discount is- " + discount);
		System.out.println("Tax is- " + tax);
		System.out.println("Final Price is- " +finalPrice);
		System.out.println();
	}
	
	public static void main(String[] args) {
		Product phone = new Electronics(1, "OPPO", 10000, 24);
		Product tshirt = new Clothing(2, "Nike", 400, "XL", "Cotton");
		Product rice = new Groceries(3, "Basmati Rice", 80, false);

		printFinalPrices(phone);
		printFinalPrices(tshirt);
		printFinalPrices(rice);
	}
}

//Abstract class
abstract class Product {
	int productId;
	String name;
	double price;

	Product(int productId, String name, double price) {
		this.productId =productId;
		this.name =name;
		this.price =price;
	}

	// Getters
	public int getProductId() {
		return productId;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}

	//Abstract method
	public abstract double calculateDiscount();

}

//Interface
interface Taxable {
	double calculateTax();
	String getTaxDetails();
}

//subclasses to implement texable 
class Electronics extends Product implements Taxable {
	Electronics(int productId, String name, double price, int warrantyMonths) {
		super(productId, name, price);
	}

	@Override
	public double calculateDiscount() {
		return getPrice()*0.05; //5% discount
	}

	@Override
	public double calculateTax() {
		return getPrice() *0.18; //18% GST
	}

	@Override
	public String getTaxDetails() {
		return "Electronics tax is 18%";
	}
}

class Clothing extends Product implements Taxable {
	Clothing(int productId, String name, double price, String size, String material) {
		super(productId, name, price);
	}

	@Override
	public double calculateDiscount() {
		return getPrice() *0.10;
	}

	@Override
	public double calculateTax() {
		return getPrice() *0.12;
	}

	@Override
	public String getTaxDetails() {
		return "Clothing tax is 12%";
	}
}

class Groceries extends Product implements Taxable {
	Groceries(int productId, String name, double price, boolean perishable) {
		super(productId, name, price);
	}

	@Override
	public double calculateDiscount() {
		return getPrice() *0.15;
	}
	
	@Override
	public double calculateTax() {
		return getPrice()*0.10;
	}
	
	@Override
	public String getTaxDetails() {
		return "Groceries tax is 10%";
	}
}