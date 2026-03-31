package DigitalWalletSystem;

public class BankTransfer implements TransferService{
	double bankFee = 20;
	public void transfer(User from,User to,double amount) throws InsufficientBalanceException{
		from.getWallet().debitBalance(amount+bankFee);
		to.getWallet().creditBalance(amount);
		System.out.println("Amount transferred successfully");
	}
}
