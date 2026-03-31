
public class BankingSystem {
	public static void main(String[] args) {
		BankAccount acc1 = new SavingsAccount(1, "Deepak", 5000);
		BankAccount acc2 = new CurrentAccount(2, "Abhay", 10000);

		//Using BankAccount reference
		System.out.println("Savings Interest is- " + acc1.calculateInterest());
		System.out.println("Current Interest is- " + acc2.calculateInterest());

		if (acc1 instanceof Loanable) {
			Loanable l = (Loanable) acc1;
			l.applyForLoan(50000);
			System.out.println("Eligible- " + l.calculateLoanEligibility());
		}
	}
}

//Abstract class
abstract class BankAccount {
	private int accountNumber;
	private String holderName;
	private double balance;

	public BankAccount(int accountNumber, String holderName, double balance) {
		this.accountNumber = accountNumber;
		this.holderName = holderName;
		this.balance = balance;
	}

	// Getters
	public int getAccountNumber() {
		return accountNumber;
	}

	public String getHolderName() {
		return holderName;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount <= balance) {
			balance -= amount;
		} else {
			System.out.println("Insufficient Balance!");
		}
	}

	// Abstract method
	public abstract double calculateInterest();
}

//Interface
interface Loanable {
	void applyForLoan(double amount);

	boolean calculateLoanEligibility();
}

//Subclass
class SavingsAccount extends BankAccount implements Loanable {
	public SavingsAccount(int accountNumber, String holderName, double balance) {
		super(accountNumber, holderName, balance);
	}

	@Override
	public double calculateInterest() {
		return getBalance() * 0.04; // 4% interest
	}

	@Override
	public void applyForLoan(double amount) {
		System.out.println("Loan applied in Savings Account for " + amount);
	}

	@Override
	public boolean calculateLoanEligibility() {
		return getBalance() > 5000;
	}
}

class CurrentAccount extends BankAccount {
	public CurrentAccount(int accountNumber, String holderName, double balance) {
		super(accountNumber, holderName, balance);
	}

	@Override
	public double calculateInterest() {
		return getBalance() * 0.02; // 2% interest
	}
}