//Write a program to input the unit price of an item and 
// the quantity to be bought. Then, calculate the total price.
import java.util.Scanner;
public class TotalPriceOfItems {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int unitPriceItem = scanner.nextInt();
        int quantityItems = scanner.nextInt();

        // here we are calculating the total price
        int totalPriceItem = unitPriceItem * quantityItems;
        System.out.println("The total purchase price is INR " +totalPriceItem+ " if the quantity "+quantityItems +" and unit price is INR "+unitPriceItem);
        scanner.close();
    }
}