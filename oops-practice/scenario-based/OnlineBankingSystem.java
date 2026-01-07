import java.util.*;
public class OnlineBankingSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Account acc1 =new SavingsAccount(1, "Deepak", 500);
        Account acc2 =new CurrentAccount(2, "Abhay", 1000);
        bank.createAccount(acc1);
        bank.createAccount(acc2);
        System.out.println();

        System.out.println("Read Account 1- " + bank.readAccount(1).getOwnerName());
        System.out.println();

        bank.updateAccount(1, "Deepak Rajput");
        bank.deleteAccount(2);
        System.out.println();

        System.out.println("Deepak's balance- " + bank.checkBalance(1));
        System.out.println();

        TransactionThread t1 = new TransactionThread(bank, 1, 1, 100);
        t1.start();

        try { 
        	t1.join(); 
        } catch (InterruptedException e) { 
        	e.printStackTrace(); 
        }

        System.out.println();
        bank.showTransactionHistory(1);
        System.out.println("Deepak's interest is- " + acc1.calculateInterest());
    }
}

//custom exception
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

//interface in abstraction to curd features
interface BankService {
    void createAccount(Account account);
    Account readAccount(int accountId);
    void updateAccount(int accountId, String newOwnerName);
    void deleteAccount(int accountId);

    double checkBalance(int accountId);
    void transferFunds(int fromId, int toId, double amount) throws InsufficientBalanceException;
    void showTransactionHistory(int accountId);
}

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

//saving account is child class
class SavingsAccount extends Account {
    public SavingsAccount(int id, String name, double balance) {
        super(id, name, balance);
    }
    @Override
    public double calculateInterest() {
        return balance*0.04;
    }
}

//current account is also child class
class CurrentAccount extends Account {
    public CurrentAccount(int id, String name, double balance) {
        super(id, name, balance);
    }
    @Override
    public double calculateInterest() {
        return balance*0.02;
    }
}

//Bank implementation to concurrent transactions
class Bank implements BankService {
    Map<Integer, Account> accounts = new HashMap<>();
    Map<Integer, List<String>> transactions = new HashMap<>();

    @Override
    public synchronized void createAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        transactions.put(account.getAccountId(), new ArrayList<>());
        System.out.println("Account created for " + account.ownerName);
    }

    @Override
    public synchronized Account readAccount(int accountId) {
        return accounts.get(accountId);
    }

    @Override
    public synchronized void updateAccount(int accountId, String newOwnerName) {
        Account account = accounts.get(accountId);
        if (account!=null) {
            account.setOwnerName(newOwnerName);
            System.out.println("Account " + accountId + " updated. New owner is- " + newOwnerName);
        } else {
            System.out.println("Account not found!");
        }
    }

    @Override
    public synchronized void deleteAccount(int accountId) {
        if (accounts.containsKey(accountId)) {
            accounts.remove(accountId);
            transactions.remove(accountId);
            System.out.println("Account " + accountId + " deleted.");
        } else {
            System.out.println("Account not found!");
        }
    }

    @Override
    public synchronized double checkBalance(int accountId) {
        return accounts.get(accountId).getBalance();
    }

    @Override
    public synchronized void transferFunds(int fromId, int toId, double amount) throws InsufficientBalanceException {
        Account from =accounts.get(fromId);
        Account to =accounts.get(toId);

        from.withdraw(amount);
        to.deposit(amount);

        transactions.get(fromId).add("Transferred " +amount + " to " +to.ownerName);
        transactions.get(toId).add("Received " +amount + " from " +from.ownerName);

        System.out.println("Transfer successful is- " +amount + " from " +from.ownerName + " to " + to.ownerName);
    }

    @Override
    public void showTransactionHistory(int accountId) {
        System.out.println("Transaction history for " + accounts.get(accountId).ownerName + "-");
        for (String t : transactions.get(accountId)) {
            System.out.println(" - " +t);
        }
    }
}

//multithreading concurrent synchronized transactions
class TransactionThread extends Thread {
    Bank bank;
    int fromId, toId;
    double amount;

    public TransactionThread(Bank bank, int fromId, int toId, double amount) {
        this.bank =bank;
        this.fromId =fromId;
        this.toId =toId;
        this.amount =amount;
    }

    @Override
    public void run() {
        try {
            bank.transferFunds(fromId, toId, amount);
        } catch (InsufficientBalanceException e) {
            System.out.println("Transaction failed- " +e.getMessage());
        }
    }
}