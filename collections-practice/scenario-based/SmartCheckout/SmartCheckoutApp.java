package SmartCheckout;
import java.util.*;
public class SmartCheckoutApp {
	public static void main(String[] args) {
		//hashMap for item prices and stock
        Map<String, Integer> priceMap=new HashMap<>();
        priceMap.put("Apple", 50);
        priceMap.put("Banana", 20);
        priceMap.put("Milk", 40);

        Map<String, Integer> stockMap=new HashMap<>();
        stockMap.put("Apple", 10);
        stockMap.put("Banana", 15);
        stockMap.put("Milk", 8);

        //adding customer in queue
        Queue<Customer> queue = new LinkedList<>();
        queue.add(new Customer("Deepak", Arrays.asList("Apple", "Milk")));
        queue.add(new Customer("Ajay", Arrays.asList("Banana", "Banana", "Milk")));
        queue.add(new Customer("Ravi", Arrays.asList("Apple", "Banana")));

        while (!queue.isEmpty()) {
        	//removing customer from queue
            Customer current =queue.poll();
            System.out.println();
            System.out.println("Customer--- "+current.name);

            int totalBill=0;
            for (String item :current.items) {
            	//fetching item prices from map
                if (stockMap.containsKey(item) && stockMap.get(item) > 0) {
                    int price =priceMap.get(item);
                    totalBill+=price;

                    //updating stock on purchase
                    stockMap.put(item, stockMap.get(item) - 1);
                    System.out.println(item +" purchased for "+price+" rs.");
                } else {
                    System.out.println(item+" is out of stock.");
                }
            }
            System.out.println("Total bill for "+current.name + " is- "+totalBill+" rs.");
        }

        System.out.println();
        System.out.println("Remaining stock are---");
        for (String item :stockMap.keySet()) {
            System.out.println(item+"- "+stockMap.get(item));
        }
	}
}
