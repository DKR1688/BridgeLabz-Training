public class Product {
	
	//instance variable
	String productName;
	int price;
	
	//class variable
	static int totalProducts=0;
	
	//here we will create constructor and increment totalProduct to create new product
	Product(String productName, int price){
		this.productName =productName;
		this.price =price;
		totalProducts++;
	}
	
	//instance method to display products
	void displayProductDetails() {
		System.out.println("Product name is- "+productName+"\n Price is- "+price);
		System.out.println();
	}
	
	//class method to display products
	static void displayTotalsDetails() {
		System.out.println("Total created products are- "+totalProducts);
		System.out.println();
	}
	
	public static void main(String[] args) {
		Product product1 =new Product("Mobile1", 10000);
		product1.displayProductDetails();
		
		Product product2 =new Product("Mobile2", 15000);
		product2.displayProductDetails();
		
		Product.displayTotalsDetails();
	}
}
