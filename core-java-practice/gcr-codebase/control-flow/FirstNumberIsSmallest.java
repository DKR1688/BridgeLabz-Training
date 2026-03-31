// a program to check if the first is the smallest of the 3 numbers
import java.util.Scanner;
public class FirstNumberIsSmallest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();
        int number3 = scanner.nextInt();

        // here we are checking if the first number is smaller than both the other numbers
        if (number1<number2 && number1<number3) {
            System.out.println("The first number "+number1+" is the smallest");
        }else{
            System.out.println("The first number "+number1+" is not the smallest");
        }
        scanner.close();
    }
    
}
