public class SimpleInterest {
    public static void main(String[] args) {
        double rate = 5.0;
        double time = 3.0;
        double principal = 1000.0;

        // this formula is to calculate simple interest
        double simpleInterest = (principal * rate * time) / 100;
        System.out.println("Simple Interest: " + simpleInterest);
    }
    
}
