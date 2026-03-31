import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@interface CacheResult {}

class ExpensiveCalculator {
    @CacheResult
    public int compute(int x) {
        System.out.println("Computing result for " + x);
        return x * x; // Simulate expensive computation
    }
}

public class CacheResultAnnotation {
    public static void main(String[] args) throws Exception {
        ExpensiveCalculator calc = new ExpensiveCalculator();
        Method m = ExpensiveCalculator.class.getDeclaredMethod("compute", int.class);
        Map<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            int input = 5;
            if (m.isAnnotationPresent(CacheResult.class) && cache.containsKey(input)) {
                System.out.println("Returning cached result for " + input);
                System.out.println("Result: " + cache.get(input));
            } else {
                int result = (int) m.invoke(calc, input);
                cache.put(input, result);
                System.out.println("Result: " + result);
            }
        }
    }
}
