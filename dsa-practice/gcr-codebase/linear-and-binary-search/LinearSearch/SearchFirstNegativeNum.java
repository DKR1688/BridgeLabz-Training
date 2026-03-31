package LinearSearch;

import java.util.*;
public class SearchFirstNegativeNum {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the size of array- ");
        int n =sc.nextInt();

        int[] arr =new int[n];
        System.out.println("Enter all numbers- ");
        for (int i=0; i<n; i++) {
            System.out.print("Enter number " +i+1+" - ");
            arr[i] =sc.nextInt();
        }

        int number =searchNegative(arr);

        if (number==0) {
            System.out.println("There are all positive numbers.");
        } else {
            System.out.println("The first negative number is "+number);
        }
        sc.close();
    }

    //method for linear search to first negative number
    public static int searchNegative(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            if (arr[i]<0) {
                return arr[i];
            }
        }
        return 0;
    }
}
