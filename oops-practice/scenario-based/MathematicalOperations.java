import java.util.*;
class MathematicalOperations {
    Scanner scanner = new Scanner(System.in);

    //method to calculate factorial of a number
    int getFactorial(int num) {
        if (num<0) {
        	return -1;
        }
        if (num==1 || num==0) {
        	return 1;
        }
        else {
        	return num * getFactorial(num-1);
        }
    }

    //method for checking prime number or not
    void isPrime() {
        System.out.print("Enter number- ");
        int num = Integer.parseInt(scanner.nextLine()); //take user input and assign in num

        if (num<=0) {
        	System.out.println("Not a prime number.");
        }
        else if (num>0) {
            int count= 0; //intillize count to check divisibility
            for (int i=2; i<=num/2; i++) {
                if (num%i == 0) {
                    count++;
                }
            }
            if (count>0) {
            	System.out.println("Not a prime number");
            }
            else {
            	System.out.println("prime number ");
            }
        }
    }

    //method to calcualte GCD of two numbers
    void getGcd() {
        System.out.print("Enter the first number- ");
        int a = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the second number- ");
        int b = Integer.parseInt(scanner.nextLine());

        while (b!=0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        System.out.println("The Gcd of two number is- "+a);
    }

    //method to generate the fibonacci series
    void getFibonacci() {
        System.out.print("Enter number- ");
        int num = Integer.parseInt(scanner.nextLine());

        if (num<0) {
        	System.out.println("Invalid input");
        }
        else if (num == 0) {
        	System.out.println("The nth fibonacci number is 0"); // if num is 0 ans is 0
        }
        else {
            if (num == 1) {
            	System.out.println("The nth fibonacci number is 1");
            }
            else {
                int a = 0;
                int b = 1;
                for (int i=2; i<=num; i++) {
                    int c = a + b;
                    a = b;
                    b = c;
                }
                System.out.println("The nth fibonacci number is " + b);
            }
        }
    }

    //main method
    public static void main(String[] args) {
        MathematicalOperations math = new MathematicalOperations();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Press 1 for factorial of a number- ");
            System.out.println("Press 2 for to check prime- ");
            System.out.println("Press 3 for GCD of two numbers- ");
            System.out.println("Press 4 for nth fibonacci number-  ");
            System.out.println("Press 5 for Exit- ");
            int choice = Integer.parseInt(scanner.nextLine()); // input user choice

            switch (choice) {
                case 1:
                    System.out.print("Enter number- ");
                    int num = Integer.parseInt(scanner.nextLine());
                    int factorial = math.getFactorial(num); // call method to find factorial
                    if (factorial== -1) {
                    	System.out.println("Invalid Input"); // if return is -1 invalid input
                    }
                    else {
                    	System.out.println("factorial = "+factorial);
                    }
                    break;

                case 2:
                    math.isPrime(); //method to find prime.
                    break;

                case 3:
                    math.getGcd(); //mehtod to get gcd.
                    break;

                case 4:
                    math.getFibonacci(); //method to get the fibonacci number.
                    break;

                case 5:
                    System.exit(0); //method to terminate the code successfully
                    break;

                default:
                    System.out.println("Invalid input"); //if user select wrong input
                    break;
            }
        } 
    }
}