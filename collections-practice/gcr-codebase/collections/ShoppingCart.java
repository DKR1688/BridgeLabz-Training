import java.util.*;
public class ShoppingCart {
    public static void main(String[] args) {
        //Use HashMap to store product prices
        Map<String, Integer> productPrices=new HashMap<>();
        productPrices.put("Apple", 50);
        productPrices.put("Banana", 20);
        productPrices.put("Orange", 30);
        System.out.println("Product prices- ");
        System.out.println(productPrices);

        //Use LinkedHashMap to maintain the order of items added
        Map<String, Integer> cart=new LinkedHashMap<>();
        cart.put("Apple", productPrices.get("Apple"));
        cart.put("Banana", productPrices.get("Banana"));
        cart.put("Orange", productPrices.get("Orange"));
        System.out.println("\nShopping cart- ");
        System.out.println(cart);

        //Use TreeMap to display items sorted by price
        TreeMap<Integer, String> sortedByPrice=new TreeMap<>();
        for (Map.Entry<String, Integer> entry :cart.entrySet()) {
            sortedByPrice.put(entry.getValue(), entry.getKey());
        }
        System.out.println("\nItems sorted by price- ");
        System.out.println(sortedByPrice);
    }
}