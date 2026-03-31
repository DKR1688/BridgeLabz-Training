package methods;

import java.util.*;
public class QuotientAndReminder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two numbers ");
        int number1 =scanner.nextInt();
        int number2 =scanner.nextInt();

        int[] ans= findRemainderAndQuotient(number1, number2);
        System.out.println("Quotient is " +ans[0]);
        System.out.println("Remainder is " +ans[1]);
        scanner.close();
    }
    
    //a Method to find the reminder and the quotient of a number 
    public static int[] findRemainderAndQuotient(int num1, int num2) {
        //Use division operator (/) for quotient and moduli operator (%) for reminder
    	int quotient = num1/num2;
        int remainder = num1%num2;
        return new int[]{quotient, remainder};
    }
	
}
