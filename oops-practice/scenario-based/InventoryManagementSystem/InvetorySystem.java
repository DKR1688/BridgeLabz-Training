package InventoryManagementSystem;

public class InvetorySystem {
	public static void main(String[] args) {
        AlertService alertService =new ConsoleAlertService();
        Inventory inventory =new Inventory(alertService);

        inventory.addOrUpdateProduct("Phone", 10);
        inventory.addOrUpdateProduct("Mouse", 5);
        inventory.addOrUpdateProduct("Laptop", 1);

        inventory.displayInventory();

        try {
            //when we will sell products then alert will be triggered.
            inventory.reduceStock("Phone", 3);
            inventory.reduceStock("Keyboard", 1);
            inventory.reduceStock("Laptop", 2);
            
        } catch (OutOfStockException e) {
            System.out.println("Exception is- " +e.getMessage());
        }
        inventory.displayInventory();
    }
}
