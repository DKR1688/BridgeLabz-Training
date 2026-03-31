package InventoryManagementSystem;

import java.util.*;
class Inventory {
	HashMap<String, Product> products =new HashMap<>();
	AlertService alertService;

	Inventory(AlertService alertService) {
		this.alertService =alertService;
	}

	//adding or updating products by name
	public void addOrUpdateProduct(String name, int quantity) {
		Product product =products.get(name);
		
		if (product ==null) {
			product = new Product(name, quantity);
			products.put(name, product);
		} else {
			product.setQuantity(product.getQuantity() +quantity);
		}
		System.out.println("Updated product is- " +product);
	}

	//reducing stock when product is sold
	public void reduceStock(String name, int quantity) throws OutOfStockException {
		Product product =products.get(name);
		
		if (product==null) {
			throw new OutOfStockException("Product "+ name+" not found.");
		}
		if (product.getQuantity()<quantity) {
			throw new OutOfStockException(name+" is insufficient.");
		}
		
		product.setQuantity(product.getQuantity() - quantity);
		
		System.out.println("Sold quantity is- " + quantity + " of " + name+ ". And remaining products are- " +product.getQuantity());

		//trigger alert if stock is less then 2
		if (product.getQuantity()<=1) {
			alertService.lowStockAlert(product);
		}
	}

	public void displayInventory() {
		System.out.println("Available inventory are- ");
		for (Product p :products.values()) {
			System.out.println(p);
		}
	}
}