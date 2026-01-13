package DigitalWalletSystem;

public interface TransferService {
	public void transfer(User from,User to,double amount) throws InsufficientBalanceException;
}
