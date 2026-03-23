package M1_Set4;

import java.util.*;

abstract class Inventory {
	protected String name;
	protected double price;
	protected int quantity;

	Inventory(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public abstract void displayDetails();

	public double getTotal() {
		return price * quantity;
	}
}

class Electronics extends Inventory {
	private int warranty;

	Electronics(String name, double price, int quantity, int warranty) {
		super(name, price, quantity);
		this.warranty = warranty;
	}

	@Override
	public void displayDetails() {
		System.out.println(
				name + " - Price: " + price + ", Quantity: " + quantity + ", Warranty: " + warranty + " months");
	}
}

class Clothing extends Inventory {
	private String size;

	Clothing(String name, double price, int quantity, String size) {
		super(name, price, quantity);
		this.size = size;
	}

	@Override
	public void displayDetails() {
		System.out.println(name + " - Price: " + price + ", Quantity: " + quantity + ", Size: " + size);
	}
}

public class SupermarketStoreInventorySystem {
	public static List<Inventory> inventory = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = Integer.parseInt(sc.nextLine());

		List<String> inputs = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			inputs.add(sc.nextLine());
		}

		for (int i = 0; i < N; i++) {
			String input = inputs.get(i);
			String[] parts = input.split(",\\s*");

			String type = parts[0];
			switch (type) {
			case "Electronics":
				inventory.add(new Electronics(parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]),
						Integer.parseInt(parts[4])));
				System.out.println("Product added to inventory: " + parts[1]);
				break;

			case "Clothing":
				inventory.add(
						new Clothing(parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), parts[4]));
				System.out.println("Product added to inventory: " + parts[1]);
				break;

			default:
				System.out.println("Invalid input");
			}
		}

		System.out.println("Inventory:");
		for (Inventory item : inventory) {
			item.displayDetails();
		}

		double total = 0.0;
		for (Inventory item : inventory) {
			total += item.getTotal();
		}
		System.out.printf("Total value of the inventory: %.2f%n", total);
	}
}