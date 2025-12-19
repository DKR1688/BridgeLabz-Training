// an IntOperation program by taking a, b, and c as input values and 
// print the following integer operations a + b *c, a * b + c, c + a / b, 
// and a % b + c. Please also understand the precedence of the operators.
import java.util.Scanner;
public class IntOperation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create variables a, b, and c of int data type.
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        // Compute 3 integer operations and assign the result to a variable
        int operation1 = a + b * c;
        int operation2 = a * b + c;
        int operation3 = c + a / b;
        int operation4 = a % b + c;

        System.out.println("The result of Int Operations are "+operation1+", "+operation2+", "+operation3+" and "+operation4);
        scanner.close();
    }
    
}
