package OnlineBankingSystem;

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

        System.out.println();
        bank.showTransactionHistory(1);
        System.out.println("Deepak's interest is- " + acc1.calculateInterest());
    }
}