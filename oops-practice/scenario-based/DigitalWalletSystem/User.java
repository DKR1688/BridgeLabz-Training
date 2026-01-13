package DigitalWalletSystem;

public class User {
	private int userId;
	private String userName;
	private Wallet wallet;
	
	public User(int userId,String userName,Wallet wallet) {
		this.userId = userId;
		this.userName = userName;
		this.wallet = wallet;
	}
	
	public int getId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Wallet getWallet() {
		return wallet;
	}
}
