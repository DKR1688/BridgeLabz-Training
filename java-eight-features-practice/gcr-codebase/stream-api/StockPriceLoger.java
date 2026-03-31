import java.util.Arrays;
import java.util.List;

public class StockPriceLoger {
    public static void main(String[] args) {

        List<Double> stockPrices = Arrays.asList(150.25, 152.30, 149.80, 151.90, 153.50, 154.00);
        System.out.println("Live Stock Price Updates:");
        stockPrices.stream().forEach(price -> System.out.println("Stock price- $" + price));

    }
}
