import java.text.SimpleDateFormat;
import java.util.Date;

class BankAccount {
	private int balance=10000;

	//it is synchronized ensures only one thread can withdraw at a time
	public synchronized boolean withdraw(String customer, int amount) {
		System.out.println("[" + customer + "] Attempting to withdraw " + amount);

		if (balance>=amount) {
			balance -= amount;
			String timeStamp =new SimpleDateFormat("HH:mm:ss").format(new Date());
			System.out.println("Transaction successful- "+customer + ", Amount- "+amount + ", Balance- "+balance+ ", Time- "+timeStamp);
			return true;
		} else {
			String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
			System.out.println("Transaction failed- "+customer + ", Amount- "+amount +", Balance- "+balance+ ", Time- "+timeStamp);
			return false;
		}
	}
}

class Transaction implements Runnable {
	private BankAccount account;
	private String customerName;
	private int amount;

	public Transaction(BankAccount account, String customerName, int amount) {
		this.account =account;
		this.customerName =customerName;
		this.amount =amount;
	}

	@Override
	public void run() {
		account.withdraw(customerName, amount);
	}
}

public class BankingSystem {
	public static void main(String[] args) {
		BankAccount account=new BankAccount();

		//creating 5 transaction threads
		Thread t1 = new Thread(new Transaction(account, "Customer-1", 3000), "Customer-1");
		Thread t2 = new Thread(new Transaction(account, "Customer-2", 4000), "Customer-2");
		Thread t3 = new Thread(new Transaction(account, "Customer-3", 2000), "Customer-3");
		Thread t4 = new Thread(new Transaction(account, "Customer-4", 5000), "Customer-4");
		Thread t5 = new Thread(new Transaction(account, "Customer-5", 1500), "Customer-5");

		System.out.println(t1.getName() + " state- " + t1.getState());
		System.out.println(t2.getName() + " state- " + t2.getState());
		System.out.println(t3.getName() + " state- " + t3.getState());
		System.out.println(t4.getName() + " state- " + t4.getState());
		System.out.println(t5.getName() + " state- " + t5.getState());

		//starting transactions
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All transactions processed.");
	}
}