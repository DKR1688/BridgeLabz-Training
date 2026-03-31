package OnlineBankingSystem;

//abstract class to inherit it
abstract class Account {
  int accountId;
  String ownerName;
  double balance;

  public Account(int accountId, String ownerName, double balance) {
      this.accountId =accountId;
      this.ownerName =ownerName;
      this.balance =balance;
  }

  public int getAccountId() { 
  	return accountId; 
  }
  
  public String getOwnerName() { 
  	return ownerName; 
  }
  
  public double getBalance() { 
  	return balance; 
  }

  public void setOwnerName(String newName) { 
  	this.ownerName = newName; 
  }

  public void deposit(double amount) {
      balance += amount;
      System.out.println(ownerName +" deposited is- " + amount);
  }

  public void withdraw(double amount) throws InsufficientBalanceException {
      if (balance < amount) {
      	throw new InsufficientBalanceException("Insufficient Balance!");
      }
      balance -= amount;
      System.out.println(ownerName + " withdrew is- " + amount);
  }
  public abstract double calculateInterest();
}
