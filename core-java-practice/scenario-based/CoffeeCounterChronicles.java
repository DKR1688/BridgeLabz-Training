// Ravi runs a café. Each customer orders different coffee types with quantities. a program
// Asks for coffee type (switch)
// Calculates total bill (price * quantity)
// Adds GST using arithmetic operators
// Use while to continue for the next customer and break when "exit" is typed. */

import java.util.*;

public class CoffeeCounterChronicles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double gstPercentage = 18;

        while (true) {
            System.out.println("Enter coffee type cappuccino, espresso, latte or type 'exit' to leave cafe: ");
            String coffeeType = scanner.nextLine();

            // if customer wants to exit then break the loop
            if (coffeeType.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for visiting Cafe, see you again!");
                break;
            }

            System.out.println("Enter quantity of "+coffeeType+" ");
            int quantity = scanner.nextInt();

            int priceOfCoffee;
            switch (coffeeType.toLowerCase()) {
                case "cappuccino":
                    priceOfCoffee = 75;
                    break;
                case "espresso":
                    priceOfCoffee = 40;
                    break;
                case "latte":
                    priceOfCoffee = 55;
                    break;
                default:
                    System.out.println("Sorry, we don't have that coffee type.");
                    continue;
            }

            double totalBill = priceOfCoffee*quantity;
            double gst = totalBill * (gstPercentage / 100);
            double totalBillWithGST = totalBill + gst;

            System.out.println("Total bill for "+quantity+" "+coffeeType+" is "+totalBillWithGST);
        }
        scanner.close();
    }
}
