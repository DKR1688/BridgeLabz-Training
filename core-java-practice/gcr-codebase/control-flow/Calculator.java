// a program to create a calculator using switch...case.

import java.util.Scanner;
public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first number and second number: ");
        double firstNumber =scanner.nextDouble();
        double secondNumber =scanner.nextDouble();

        System.out.print("Enter operator (+, -, *, /, %): ");
        String operator =scanner.next();

        // here we use switch...case to perform calculator operations
        double ans;
        switch (operator) {
            case "+":
                ans =  firstNumber+secondNumber;
                System.out.println("Answer of "+firstNumber+ " + "+secondNumber+" = "+ans);
                break;
            case "-":
                ans = firstNumber-secondNumber;
                System.out.println("Answer of "+firstNumber+" - "+secondNumber+" = "+ ans);
                break;
            case "*":
                ans = firstNumber*secondNumber;
                System.out.println("Answer of "+firstNumber+" * "+secondNumber+" = "+ans);
                break;
            case "/":
                if (secondNumber!=0) {
                    ans = firstNumber/secondNumber;
                    System.out.println("Answer of "+firstNumber+" / "+secondNumber+" = "+ans);
                } else {
                    System.out.println("Division by zero not possible");
                }
                break;
            case "%":
                ans = firstNumber%secondNumber;
                System.out.println("Result: " + ans);
                break;
            // if operator is other than +, -, *, /, %
            default:
                System.out.println("Invalid Operator");
                break;
        }

        scanner.close();
    }
}