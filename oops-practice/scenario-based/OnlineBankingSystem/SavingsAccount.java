package OnlineBankingSystem;

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