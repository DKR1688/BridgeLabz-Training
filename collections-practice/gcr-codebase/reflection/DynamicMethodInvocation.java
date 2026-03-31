import java.lang.reflect.*;
import java.util.Scanner;

class MathOperations {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
}

public class DynamicMethodInvocation {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        MathOperations ops = new MathOperations();
        System.out.print("Enter method name (add/subtract/multiply): ");
        String methodName = sc.nextLine();
        System.out.print("Enter first int: ");
        int a = sc.nextInt();
        System.out.print("Enter second int: ");
        int b = sc.nextInt();
        Method m = MathOperations.class.getMethod(methodName, int.class, int.class);
        int result = (int) m.invoke(ops, a, b);
        System.out.println("Result: " + result);
    }
}

