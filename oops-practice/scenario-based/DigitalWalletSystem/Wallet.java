package DigitalWalletSystem;
import java.util.*;
public class Wallet{
	private double balance;
	private ArrayList<Transaction> transactionHistory = new ArrayList<>();
	public Wallet(double balance) {
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void creditBalance(double amount) {
		if(amount > 0) {
			balance += amount;
			transactionHistory.add(new Transaction("CREDIT", amount));
		}
	}
	
	public void debitBalance(double amount) throws InsufficientBalanceException{
		if((balance - amount) < 0) {
			throw new  InsufficientBalanceException(" Insufficient Balance in your account");
		}
		balance -= amount;
		transactionHistory.add(new Transaction("DEBIT", amount));
	}
	public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Transaction History:");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }
}
