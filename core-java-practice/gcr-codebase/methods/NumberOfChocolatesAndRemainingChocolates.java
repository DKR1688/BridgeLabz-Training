package methods;

import java.util.*;
public class NumberOfChocolatesAndRemainingChocolates {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter number of chocolates and children");
        int numberOfchocolates= scanner.nextInt();
        int numberOfchildren =scanner.nextInt();

        int[] ans= findRemainderAndQuotient(numberOfchocolates, numberOfchildren);
        System.out.println("Each child gets " +ans[0]+ " chocolates");
        System.out.println("Remaining chocolates are " +ans[1]);
        scanner.close();
    }
	
	// method to find the number of chocolates each child gets and number of remaining chocolates
	public static int[] findRemainderAndQuotient(int chocolates, int children) {
        int eachChildChocolates = chocolates/children;
        int remainingChocolates = chocolates%children;
        return new int[]{eachChildChocolates, remainingChocolates};
    }
}
