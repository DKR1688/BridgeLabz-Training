package OnlineBankingSystem;

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