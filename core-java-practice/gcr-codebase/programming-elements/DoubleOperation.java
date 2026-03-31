// an DoubleOperation program by taking a, b, and c as input values and 
// print the following double operations a + b *c, a * b + c, c + a / b, 
// and a % b + c.
import java.util.Scanner;
public class DoubleOperation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create variables a, b, and c of double data type.
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        // Compute 3 integer operations and assign the result to a variable
        double operation1 = a + b * c;
        double operation2 = a * b + c;
        double operation3 = c + a / b;
        double operation4 = a % b + c;

        System.out.println("The result of Double type values Operations are "+operation1+", "+operation2+", "+operation3+" and "+operation4);
        scanner.close();
    }
    
}
