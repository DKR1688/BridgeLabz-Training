package OnlineBankingSystem;

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