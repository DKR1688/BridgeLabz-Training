class BankAccount {
    public String accountNumber; 
    protected String accountHolder;
    private double balance;
    
    BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber =accountNumber;
        this.accountHolder =accountHolder;
        this.balance =balance;
    }

    //this methods is to access and modify balance
    public double getBalance() {
        return balance;
    }

    void deposit(double amount) {
        if (amount>0) {
            balance += amount;
            System.out.println("Deposited is- " +amount);
        } else {
            System.out.println("No deposit amount!");
        }
    }

    void withdraw(double amount) {
        if (amount>0 && amount<=balance) {
            balance -= amount;
            System.out.println("Withdrawn is- " + amount);
        } else {
            System.out.println("insufficient balance!");
        }
    }

    void displayAccount() {
        System.out.println("Account number is- " +accountNumber);
        System.out.println("Account holder is- " +accountHolder);
        System.out.println("Balance is- " +balance);
    }
    
    public static void main(String[] args) {
        SavingsAccount account= new SavingsAccount("123456789", "Deepak Kumar", 5000.0, 4.5);

        System.out.println("Before account details are- ");
        account.displayAccount();
        System.out.println();

        // Performing transactions
        account.deposit(1500);
        account.withdraw(2000);

        System.out.println();
        System.out.println("Updated account details- ");
        account.displayAccount();

        System.out.println();
        System.out.println("Savings account access modifiers demonstration- ");
        account.displaySavingsAccount();
    }
}

class SavingsAccount extends BankAccount {
    double rate;

    SavingsAccount(String accountNumber, String accountHolder, double balance, double rate) {
        super(accountNumber, accountHolder, balance);
        this.rate =rate;
    }

    //demonstrating access modifiers
    void displaySavingsAccount() {
        System.out.println("Savings account number is- " +accountNumber);
        System.out.println("Savings account holder is- " +accountHolder);

        //cannot access private balance must use getter
        System.out.println("Savings account balance is- " +getBalance());
        System.out.println("Interest Rate is- " +rate+ "%");
    }
}