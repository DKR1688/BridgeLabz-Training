package DigitalWalletSystem;

public class WalletSystem {

	public static void main(String[] args) {
		
		try {
			Wallet wallet1 = new Wallet(3000);
			Wallet wallet2 = new Wallet(5000);
			
			User user1 = new User(101,"Deepak Rajput",wallet1);
			User user2 = new User(102,"Ankit",wallet2);
			
			TransferService walletTransfer = new WalletTransfer();
			walletTransfer.transfer(user2, user1, 750);
			
			 System.out.println(user1.getUserName() + " Balance is- " + user1.getWallet().getBalance());
	         System.out.println(user2.getUserName() + " Balance is- " + user2.getWallet().getBalance());
	         
	         user1.getWallet().showTransactionHistory();
	         user2.getWallet().showTransactionHistory();
		
		}catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }	

	}

}
