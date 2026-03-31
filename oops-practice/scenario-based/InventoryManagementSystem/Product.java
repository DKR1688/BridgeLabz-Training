package InventoryManagementSystem;

class Product {
    String name;
    int quantity;

    Product(String name, int quantity) {
        this.name =name;
        this.quantity =quantity;
    }

    public String getName() { 
    	return name; 
    }
    public int getQuantity() { 
    	return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity =quantity;
    }

    @Override
    public String toString() {
        return name + " Stock quantity is- " +quantity;
    }
}