package BankAccount;

public class Program {
	private double balance;

	Program(double initialBalance) {
		this.balance=initialBalance;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		if (amount<0) {
			throw new IllegalArgumentException("Deposit amount cannot be negative.");
		}
		balance+=amount;
	}

	public void withdraw(double amount) {
		if (amount>balance) {
			throw new IllegalStateException("Insufficient funds.");
		}
		balance-=amount;
	}
}
