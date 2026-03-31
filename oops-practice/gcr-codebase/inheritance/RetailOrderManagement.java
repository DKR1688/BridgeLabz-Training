
public class RetailOrderManagement {
	public static void main(String[] args) {
	     Order order = new Order(1, "2026-01-03");
	     System.out.println(order.getOrderStatus());
	     
	     ShippedOrder shippedOrder = new ShippedOrder(102, "2026-01-02", "TRK12345");
	     System.out.println(shippedOrder.getOrderStatus());
	     
	     DeliveredOrder deliveredOrder = new DeliveredOrder(103, "2026-01-03", "TRK67890", "2026-01-05");
	     System.out.println(deliveredOrder.getOrderStatus());
	 }
}

class Order {
 int orderId;
 String orderDate;

 	public Order(int orderId, String orderDate) {
 		this.orderId =orderId;
 		this.orderDate =orderDate;
 	}

 	public String getOrderStatus() {
 		return "Order placed- "+orderId+", "+orderDate;
 	}
}

class ShippedOrder extends Order {
	String trackingNumber;

	public ShippedOrder(int orderId, String orderDate, String trackingNumber) {
		super(orderId, orderDate);
		this.trackingNumber =trackingNumber;
	}

	@Override
	public String getOrderStatus() {
		return "Order shipped. Tracking No is- " +trackingNumber;
	}
}

class DeliveredOrder extends ShippedOrder {
	String deliveryDate;

	public DeliveredOrder(int orderId, String orderDate, String trackingNumber, String deliveryDate) {
		super(orderId, orderDate, trackingNumber);
		this.deliveryDate =deliveryDate;
	}

	@Override
	public String getOrderStatus() {
		return "Order delivered date- " + deliveryDate;
	}
}
