import java.util.*;
public class MultipleCatchBlocks {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
        int[] arr={1, 2, 3, 4, 5, 7};

        System.out.print("Enter the index to retrieve value- ");
        int index =sc.nextInt();
        try {
            int value =arr[index];
            System.out.println("Value at index "+index + " is- "+value);
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index is out of bound.");
        } 
        catch (NullPointerException e) {
            System.out.println("Array is empty.");
        }
        sc.close();
	}
}
