import java.util.*;
class BankingSystem {
    Map<Integer, Double> accounts=new HashMap<>();
    TreeMap<Double, Integer> sortedAccounts=new TreeMap<>();
    Queue<Integer> withdrawalQueue=new LinkedList<>();

    //hashMap stores customer accounts (AccountNumber -> Balance)
    void addAccount(int accNo, double balance) {
        accounts.put(accNo, balance);
        sortedAccounts.put(balance, accNo);
    }

    //treeMap sorts customers by balance
    void showSortedAccounts() {
        for (Map.Entry<Double, Integer> e:sortedAccounts.entrySet()) {
            System.out.println("Account- "+e.getValue() + ", Balance- "+e.getKey());
        }
    }

    //requesting withdrawal
    void requestWithdrawal(int accNo) {
        withdrawalQueue.add(accNo);
    }

    //queue processes withdrawal requests
    void processWithdrawals() {
        while (!withdrawalQueue.isEmpty()) {
            int accNo=withdrawalQueue.poll();
            System.out.println("Processing withdrawal for Account "+accNo);
        }
    }

    public static void main(String[] args) {
        BankingSystem bank=new BankingSystem();

        bank.addAccount(12345678, 2000);
        bank.addAccount(56789234, 3000);
        bank.addAccount(45612387, 10000);

        bank.showSortedAccounts();

        bank.requestWithdrawal(12345678);
        bank.requestWithdrawal(45612387);

        bank.processWithdrawals();
    }
}