import java.util.*;
public class CafeteriaMenuApp {

    // Store items in an array
    static String[] menu = {"1. Poha",
    						"2. Veg Thali",
    						"3. Burger",
    						"4. Ice Cream",
    						"5. Chole bhature",
    						"6. Rice",
    						"7. Tea",
    						"8. Spring roll",
    						"9. Pasta",
    						"10. Lassi"};

    //this method is to display menu
    public static void displayMenu() {
        System.out.println("Cafeteria Menu --- ");
        for (int i=0; i<menu.length; i++) {
            System.out.println(menu[i]);
        }
    }

    //method is to get item by index
    public static String getItemByIndex(int index) {
        if (index>0 && index<menu.length) {
            return menu[index-1];
        } else {
            return "Invalid choice!";
        }
    }

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        displayMenu();

        System.out.print("Enter item number to order- ");
        int choice = scanner.nextInt();

        // Show selected item
        System.out.println("Your selected order is- " +getItemByIndex(choice));
        System.out.println("Order placed successfully. Enjoy your meal!");
        scanner.close();
    }
}