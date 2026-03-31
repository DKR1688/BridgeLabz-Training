public class CartItem {
	String itemName;
	double price;
	int quantity;
	
	CartItem(String itemName, double price, int quantity){
		this.itemName =itemName;
		this.price =price;
		this.quantity =quantity;
	}
	
	//this method is to add item to cart
	void addItem(int item) {
		quantity += item;
		System.out.println("Added " +item+ " of " +itemName+ " to the cart.");
	}
	
	//this method is to remove item from cart
	void removeItem(int item) {
		quantity -= item;
		System.out.println("Removed " +item+ " of " +itemName+ " from the cart.");
	}
	
	//this method is to calculate total cost of items
	double totalCost() {
		return quantity*price;
	}
	
	void displayItem() {
		System.out.println("Item- " +itemName+ ", Price- $" +price+ ", Quantity- " +quantity);
		addItem(2);
		removeItem(1);
		System.out.println("Total cost: $" +totalCost());
	}
	
	public static void main(String[] args) {
		CartItem cart =new CartItem("Laptop", 999.99, 1);
		cart.displayItem();
	}
}
