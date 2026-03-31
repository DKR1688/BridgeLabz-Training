import java.util.*;
public class BasicCalculator{
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("Basic Calculator");
        System.out.println("1. Addition, 2. Subtraction, 3. Multiplication, 4. Division, 5. Modular \n"+
        					"Choose an operation from 1 to 5 - ");
        int choosen= scanner.nextInt();

        System.out.print("Enter first and second number - ");
        double num1 = scanner.nextDouble();
        double num2 = scanner.nextDouble();

        double ans=0;
        switch (choosen) {
            case 1:
                ans= addition(num1, num2);
                break;
            case 2:
                ans= subtraction(num1, num2);
                break;
            case 3:
                ans= multiplication(num1, num2);
                break;
            case 4:
                ans= division(num1, num2);
                break;
            case 5:
            	ans= modular(num1, num2);
            	break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        printAns(ans);
        scanner.close();
    }

    static double addition(double num1, double num2) {
        return num1+num2;
    }

    static double subtraction(double num1, double num2) {
        return num1-num2;
    }

    static double multiplication(double num1, double num2) {
        return num1*num2;
    }

    static double division(double num1, double num2) {
        if (num2==0) {
            System.out.println("Division by zero is not possible.");
            return -1;
        }
        return num1/num2;
    }
    
    static double modular(double num1, double num2) {
    	return num1%num2;
    }

    static void printAns(double ans) {
        System.out.println("Ans = " +ans);
    }
}
