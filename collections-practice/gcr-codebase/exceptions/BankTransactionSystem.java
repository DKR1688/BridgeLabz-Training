import java.util.*;
public class BankTransactionSystem {
	public static void main(String[] args) {
		BankAccount bank=new BankAccount(500);
		try {
			bank.withdraw(100);
			bank.withdraw(1000);
			
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}catch(InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}
}

class InsufficientBalanceException extends Exception {
	InsufficientBalanceException(String message){
		super(message);
	}
}

class BankAccount{
	double balance;
	
	BankAccount(double balance){
		this.balance=balance;
	}
	
	//a method to withdraw with exception
	public void withdraw(double amount) throws InsufficientBalanceException{
		if(amount<0) {
			throw new IllegalArgumentException("Amount is invalid.");
		}
		if(amount>balance) {
			throw new InsufficientBalanceException("Balance insufficient.");
		}
		balance -=amount;
		System.out.println("Withdraw successful.");
	}
}