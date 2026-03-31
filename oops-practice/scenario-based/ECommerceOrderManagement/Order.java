package ECommerceOrderManagement;

//Order Class
class Order {
	int orderId;
	Customer customer;
	Product product;

	public Order(int orderId, Customer customer, Product product) {
		this.orderId = orderId;
		this.customer = customer;
		this.product = product;
	}

	boolean isCancelled = false;
	public void cancelOrder() {
		isCancelled = true;
		System.out.println("Order " + orderId+" has been cancelled.");
	}

	public void trackOrder() {
		if (isCancelled) {
			System.out.println("Order " + orderId+" is cancelled.");
		} else {
			System.out.println("Order " + orderId+" is on the way!");
		}
	}
}
