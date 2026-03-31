public class Item {
	String itemCode;
    String itemName;
    double price;

    public Item(String itemCode, String itemName, double price) {
        this.itemCode =itemCode;
        this.itemName =itemName;
        this.price =price;
    }

    //this method is to calculate total cost for given quantity
    double totalCost(int quantity) {
        return price*quantity;
    }

    public void display() {
        System.out.println("itemCode- " +itemCode);
        System.out.println("itemPrice- " +price);
        System.out.println("itemName- " +itemName);
        System.out.println("-------------------------");
    }
    
    public static void main(String[] args) {
        Item item1 = new Item("01AA", "Water bottle", 500.0);
        Item item2 = new Item("01BB", "Rice", 700.0);
        Item item3 = new Item("02AA", "blackboard", 400.0);

        item1.display();
        item2.display();
        item3.display();

        int quantity=3;
        System.out.println("Total cost of water bottles is- " + item1.totalCost(quantity));
    }

}
