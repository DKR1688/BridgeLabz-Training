package InventoryManagementSystem;
//ConsoleAlertService.java (Implementation of AlertService)
class ConsoleAlertService implements AlertService {
 @Override
 public void lowStockAlert(Product product) {
     System.out.println("⚠️ ALERT: Low stock for " + product.getName() +
                        " (Remaining: " + product.getQuantity() + ")");
 }
}