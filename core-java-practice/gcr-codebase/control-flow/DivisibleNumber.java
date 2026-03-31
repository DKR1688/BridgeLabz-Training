// a program to check if a number is divisible by 5
import java.util.Scanner;
public class DivisibleNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number  = scanner.nextInt();

        // here we are using if-else statement to check divisibility
        if(number % 5 == 0){
            System.out.println("The number "+number+" is divisible by 5");
        } else {
            System.out.println("The number "+number+" is not divisible by 5");
        }
        scanner.close();
    }
}