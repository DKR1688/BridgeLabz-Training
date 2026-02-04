import java.util.function.Function;

public class StringLengthChecker {
    public static void main(String[] args) {

        String message = "Welcome to interfaces in Java";
        int limit = 36;

        //function to calculate length of a string
        Function<String, Integer> len =msg -> msg.length();

        int length = len.apply(message);
        if (length > limit) {
            System.out.println(" Message exceeds character limit.");
        } else {
            System.out.println(" Message is within character limit.");
        }
    }
}
