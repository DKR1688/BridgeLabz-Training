
public class ExceptionPropagationInMethods {
	public static void main(String[] args) {
		//Calls method2() and handles the exception.
        try {
            method2();
        } catch (ArithmeticException e) {
            System.out.println("Handled exception in main.");
        }
    }
	
	//method1 to throws an ArithmeticException
    public static void method1() {
        int ans =10/0;
        System.out.println("Result- "+ans);
    }

    //method2 to calls method1
    public static void method2() {
        method1();
    }
}
