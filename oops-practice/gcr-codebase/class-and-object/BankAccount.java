public class BankAccount {
	String accountHolder;
    String accountNumber;
    double balance;

    public BankAccount(String accountHolder, String accountNumber, double balance) {
        this.accountHolder =accountHolder;
        this.accountNumber =accountNumber;
        this.balance =balance;
    }

    //this method is to deposit money
    void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited- " + amount);
        displayBalance();
    }

    //this method is to withdraw money (only if sufficient balance exists)
    void withdraw(double amount) {
        if (balance>=amount) {
            balance -= amount;
            System.out.println("Withdraw amount is- " + amount);
            displayBalance();
        } else {
            System.out.println("Insufficient balance");
        }
    }
    
    //this method is to display current balance
    public void displayBalance() {
        System.out.println("Current balance- " +balance);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("State of Chennai", "ACC001", 700.0);
        //Displaying initial balance
        System.out.println("State of Chennai");
        account.displayBalance();

        //here we will deposit money
        account.deposit(200.0);

        //here we will withdraw money
        account.withdraw(100.0);

        //when we will try to attempt to withdraw more than balance
        //then output will be insufficient balance
        account.withdraw(1000.0);
    }
}
