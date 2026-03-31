/*Metro Smart Card Fare Deduction 🚇
In Delhi Metro, fare varies by distance.
● Ask the user for distance.
● Calculate fare using ternary operator.
● Deduct from smart card balance.
Loop until balance is exhausted or the user quits.*/

import java.util.*;
public class SmartCardFareDeduction {
	public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.print("Enter your smart card balance ");
        int cardBalance =scanner.nextInt();

        //Loop until balance is exhausted or the user quits
        while (cardBalance>0) {
        	
        	//In Delhi Metro, fare varies by distance.
            System.out.println("Enter your distance in km or -1 to exit ");
            int distance =scanner.nextInt();
            if (distance==-1) {
            	break;
            }

            //Calculate fare using ternary operator.
            int fare =(distance <= 10) ? 15 : 20;

            // Checking for balance
            if (cardBalance>=fare) {
            	
            	//Deduct from smart card balance.
                cardBalance -= fare;
                System.out.println("Deducted fare is "+fare);
                System.out.println("Remaining balance is "+cardBalance);
            } else {
                System.out.println("Your card have insufficient balance");
                break;
            }
        }
        scanner.close();
    }

}
