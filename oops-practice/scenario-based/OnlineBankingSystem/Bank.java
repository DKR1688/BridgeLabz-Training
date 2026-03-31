package OnlineBankingSystem;
import java.util.*;
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
