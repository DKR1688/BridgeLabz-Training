import java.util.*;
public class NestedTryCatchBlock {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int[] arr ={45, 21, 41, 12, 78, 455};

        System.out.print("Enter index to access value- ");
        int index =sc.nextInt();

        System.out.print("Enter divisor- ");
        int divisor =sc.nextInt();
        try {
            int value=arr[index];
            try {
                int ans =value/divisor;
                System.out.println("Division result is- " +ans);
            } catch (ArithmeticException e) {
                System.out.println("Cannot divide by zero.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid array index.");
        }
        sc.close();
    }
}
