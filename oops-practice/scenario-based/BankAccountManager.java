import java.util.*;
class BankAccountManager {
    static float balance = 10000;
    static String accountNumber ="BOB0001";
    static String password = "123456";

    static Scanner scanner = new Scanner(System.in);

    //method for deposite the amount to the balance
    static void Deposit() {
        System.out.println("Enter Password- ");
        String pass = scanner.nextLine();
        if (pass.equals(password)) {
            System.out.println("Enter the amount you want to deposite- ");
            float amount = Float.parseFloat(scanner.nextLine());

            if (amount<=0) {
                System.out.println("Please enter the valid amount.");
            } else {
                System.out.println(amount + " is successfully deposite in your account");
                balance += amount;
            }
        } else {
            System.out.println("Wrong password");
        }
    }

    //mehtod to withdraw the amount
    public static void Withdraw() {
        System.out.println("Enter Password: ");
        String pass = scanner.nextLine();
        if (pass.equals(password)) {
            System.out.println("Enter the amount you want to withdraw ");
            float amount = Float.parseFloat(scanner.nextLine()); 

            if (balance<amount) {
                System.out.println("Low balance");
            } else {
                balance -= amount;
                System.out.println("Withdraw successfull");
            }
        } else {
            System.out.println("Wrong Password");
        }
    }

    //method to check balance
    static void CheckBalance() {
        System.out.println("Enter Password- ");
        String pass = scanner.nextLine();
        if (pass.equals(password)) {
            System.out.println("Balance is- " + balance);
        } else {
            System.out.println("Wrong password");
        }
    }

    //method for display
    static void display() {
            while (true) {
            System.out.println("press 1 to check balance- ");
            System.out.println("press 2 to deposite amount- ");
            System.out.println("press 3 to withdraw money- ");
            System.out.println("press 4 to Exit- ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    CheckBalance();  // method for check balance
                    break;

                case 2:
                    Deposit(); //method for deposite amount
                    break;

                case 3:
                    Withdraw(); //mehtod for withdraw the amount
                    break;

                case 4:
                    System.exit(0); //terminatte the code successfully
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Please enter your account number- ");
        String number = scanner.nextLine();
        
      //check if account number is correct or not
        if (accountNumber.equals(number)) {
            display(); // call display method in bankaccount class
        } else {
            System.out.println("Invalid account number");
        }
        scanner.close();
    }
}