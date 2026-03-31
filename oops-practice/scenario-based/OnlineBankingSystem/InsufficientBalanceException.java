package OnlineBankingSystem;

//custom exception
class InsufficientBalanceException extends Exception {
  public InsufficientBalanceException(String message) {
      super(message);
  }
}
