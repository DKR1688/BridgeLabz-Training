import java.util.*;
import java.util.stream.Collectors;
public class OrderRevenueSummary {
	public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
            new Order("Abhay", 120.50),
            new Order("Deepak", 75.00),
            new Order("Ganni", 200.00),
            new Order("Sachin", 50.25)
        );

        //grouping by customer and sum order amounts
        Map<String, Double> revenueByCustomer =orders.stream().collect(Collectors.groupingBy(
                Order::getCustomer, Collectors.summingDouble(Order::getAmount)));

        revenueByCustomer.forEach((customer, total) -> System.out.println(customer+" - "+total));
    }
}

class Order {
    String customer;
    double amount;

    Order(String customer, double amount) {
        this.customer = customer;
        this.amount = amount;
    }

    public String getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }
}
