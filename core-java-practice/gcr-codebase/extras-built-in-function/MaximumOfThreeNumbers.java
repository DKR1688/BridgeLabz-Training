import java.util.*;
public class MaximumOfThreeNumbers {
	public static void main(String[] args) {
		
        int[] numbers =userNumbers();

        int maximum = findMax(numbers[0], numbers[1], numbers[2]);

        System.out.println("The maximum of three numbers is - "+maximum);
    }

    // this function to take three numbers from the user
    static int[] userNumbers() {
        Scanner scanner= new Scanner(System.in);
        int[] numbers = new int[3];
        
        System.out.print("Enter the first, second and third number - ");
        numbers[0] = scanner.nextInt();
        numbers[1] = scanner.nextInt();
        numbers[2] = scanner.nextInt();

        return numbers;
    }

    // this function to find the maximum of three numbers
    public static int findMax(int num1, int num2, int num3) {
        int max=num1;
        if (num2>max) {
            max=num2;
        }
        if (num3>max) {
            max=num3;
        }
        return max;
    }
}