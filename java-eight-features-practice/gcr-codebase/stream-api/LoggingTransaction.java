import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class LoggingTransaction {
	public static void main(String[] args) {
		List<Number> transactionIds = Arrays.asList(980.45,234,346.76,234.890,48.89099);
		transactionIds.forEach(id -> System.out.println(LocalTime.now() + " - Transaction- " + id));
	}
}
