import java.util.Arrays;
import java.util.List;
class Invoice {
    String transactionId;

    Invoice(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Invoice generated for transaction ID- " + transactionId;
    }
}

public class InvoiceObjectCreation {
    public static void main(String[] args) {
        List<String> transactionIds =Arrays.asList("T001", "T002", "T003");

        List<Invoice> invoices =transactionIds.stream().map(Invoice::new).toList();
        invoices.forEach(System.out::println);
    }
}
