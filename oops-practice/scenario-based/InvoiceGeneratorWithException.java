import java.util.*;
//we will ustom exception class
class InvalidInvoiceFormatException extends Exception {
    public InvalidInvoiceFormatException(String message) {
        super(message);
    }
}

public class InvoiceGeneratorWithException {

    //this method to splitting input into tasks
    public static String[] parseInvoice(String input) {
        //spliting by comma to get each "Task - Amount INR"
        String[] parts = input.split(",");
        //Trim each part to clean spaces
        for (int i=0; i<parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    //this method is to calculate total amount
    public static int getTotalAmount(String[] tasks) throws InvalidInvoiceFormatException {
        int total=0;
        for (String task : tasks) {
            
            //each task "Logo Design - 3000 INR"
            String[] nameAndAmount =task.split("-");
            if (nameAndAmount.length < 2) {
                //Skip malformed entries
                throw new InvalidInvoiceFormatException("Invalid format, Missing '-' in task: " +task);
            }
            
            String amountPart =nameAndAmount[1].trim(); // "3000 INR"
            String digits = amountPart.replaceAll("[^0-9]", ""); // keep only numbers
            if (digits.isEmpty()) {
                throw new InvalidInvoiceFormatException("Invalid format, No amount found in task- " + task);
            }
            total += Integer.parseInt(digits);
        }
        return total;
    }

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter invoice details- ");
        String details= scanner.nextLine();

        try {
            //Parsing tasks
            String[] tasks =parseInvoice(details);

            System.out.println("Tasks are- ");
            for (String task : tasks) {
                System.out.println(task.trim());
            }
            int total =getTotalAmount(tasks);
            
            System.out.println("Total amount is- "+total+" INR");
        } catch (InvalidInvoiceFormatException e) {
            System.out.println("Error is- "+e.getMessage());
        }
        scanner.close();
    }
}