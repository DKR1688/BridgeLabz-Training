package M1_Outside;

import java.util.*;
public class CreditCardAccountManagement {
	static List<CreditCard> cards=new ArrayList<>();
	public static int issueCard(String number, String name, int creditLimit) {
		for(CreditCard c: cards) {
			if(c.cardNum.equals(number)) {
				return 0;
			}
		}
		cards.add(new CreditCard(number, name, creditLimit));
		return 1;
	}
	
	public static int spendAmount(String cardNumber, int amount) {
		for(CreditCard c:cards) {
			if(c.cardNum.equals(cardNumber)) {
				if(amount<=c.availableLimit) {
					c.availableLimit=c.availableLimit-amount;
					c.addTransaction("SPEND", amount);
					System.out.println("SPENT " + cardNumber + " " + c.availableLimit);
				}else {
					System.out.println("Transaction declined");
				}
				return 1;
			}
		}
		System.out.println("Transaction declined");
		return 0;
	}
	
	public static int makePayment(String number, int amount) {
		for (CreditCard c : cards) {
		    if (c.cardNum.equals(number)) {
		        c.availableLimit = Math.min(c.creditLimit, c.availableLimit + amount);
		        c.addTransaction("PAYMENT", amount);
		        System.out.println("PAYMENT DONE " + number + " " + c.availableLimit);
		        return 1;
		    }
		}
		System.out.println("Card not found");
		return 0;
	}
	
	public static int getCardsByHolder(String name) {
		List<CreditCard> holderCards=new ArrayList<>();
		for(CreditCard c:cards) {
			if(c.cardHolderName.equals(name)) {
				holderCards.add(c);
			}
		}
		
		if(holderCards.isEmpty()) {
			System.out.println("No cards found");
			return 0;
		}
		
		holderCards.sort(Comparator.comparing(c -> c.cardNum));
		for(CreditCard c:holderCards) {
			System.out.println(c.cardNum+" "+c.availableLimit);
		}
		
		return 1;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());
        
        List<String> inputs=new ArrayList<>();
        for(int i=0; i<N; i++) {
        	inputs.add(sc.nextLine());
        }

        for (int i = 0; i < N; i++) {
        	String input=inputs.get(i);
            String[] parts = input.split(" ");
            String command = parts[0];

            switch (command) {
                case "ISSUE":
                    issueCard(parts[1], parts[2], Integer.parseInt(parts[3]));
                    break;
                case "SPEND":
                    spendAmount(parts[1], Integer.parseInt(parts[2]));
                    break;
                case "PAYMENT":
                    makePayment(parts[1], Integer.parseInt(parts[2]));
                    break;
                case "HOLDER":
                    getCardsByHolder(parts[1]);
                    break;
                default:
                    break;
            }
        }
	}
	
}

class Transaction{
	String type;
	int amount;
	int remainingLimit;
	
	public Transaction(String type, int amount, int remainingLimit) {
		super();
		this.type = type;
		this.amount = amount;
		this.remainingLimit = remainingLimit;
	}
}

class CreditCard{
	String cardNum;
	String cardHolderName;
	int creditLimit;
	int availableLimit;
	List<Transaction> transactions;
	
	public CreditCard(String cardNum, String cardHolderName, int creditLimit) {
		super();
		this.cardNum = cardNum;
		this.cardHolderName = cardHolderName;
		this.creditLimit = creditLimit;
		this.availableLimit = creditLimit;
		this.transactions=new ArrayList<>();
	}
	
	public void addTransaction(String type, int amount) {
		transactions.add(new Transaction(type, amount, availableLimit));
	}
}