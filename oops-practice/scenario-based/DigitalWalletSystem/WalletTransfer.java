package DigitalWalletSystem;

public class WalletTransfer implements TransferService{
	@Override
	public void transfer(User from,User to,double amount) throws InsufficientBalanceException{
		from.getWallet().debitBalance(amount);
		to.getWallet().creditBalance(amount);
		System.out.println("Amount transferred successfully");
	}
}
