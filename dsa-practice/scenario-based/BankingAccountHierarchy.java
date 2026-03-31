import java.util.*;

public class BankingAccountHierarchy {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankAccount account = null;

		while (true) {
			System.out.println("Banking Menu---");
			System.out.println("1. Create Savings Account");
			System.out.println("2. Create Checking Account");
			System.out.println("3. Show Account Details");
			System.out.println("4. Calculate Fee");
			System.out.println("5. Exit");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter account number- ");
				String sAccNo = sc.next();
				System.out.print("Enter balance- ");
				double sBal = sc.nextDouble();
				account = new SavingsAccount(sAccNo, sBal);
				System.out.println("Savings account created successfully!");
				break;

			case 2:
				System.out.print("Enter account number- ");
				String cAccNo = sc.next();
				System.out.print("Enter balance- ");
				double cBal = sc.nextDouble();
				account = new CheckingAccount(cAccNo, cBal);
				System.out.println("Checking account created successfully!");
				break;

			case 3:
				if (account != null) {
					System.out.println("Account details- " + account);
				} else {
					System.out.println("No account created yet!");
				}
				break;

			case 4:
				if (account != null) {
					System.out.printf("Transaction Fee: %.2f%n", account.calculateFee());
				} else {
					System.out.println("No account created yet!");
				}
				break;

			case 5:
				System.out.println("Exiting...");
				sc.close();
				return;

			default:
				System.out.println("Invalid choice! Try again.");
			}
		}
	}
}

//Abstract base class
abstract class BankAccount {
	private final String accountNumber;
	private final double balance;

	public BankAccount(String accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public abstract double calculateFee();

	@Override
	public String toString() {
		return "AccountNumber: " + accountNumber + ", Balance: " + balance;
	}
}

//SavingsAccount charges 0.5% of balance
class SavingsAccount extends BankAccount {
	public SavingsAccount(String accountNumber, double balance) {
		super(accountNumber, balance);
	}

	@Override
	public double calculateFee() {
		return getBalance() * 0.005;
	}
}

//CheckingAccount charges flat 1.0 if balance < 1000, else 0
class CheckingAccount extends BankAccount {
	public CheckingAccount(String accountNumber, double balance) {
		super(accountNumber, balance);
	}

	@Override
	public double calculateFee() {
		return getBalance() < 1000 ? 1.0 : 0.0;
	}
}