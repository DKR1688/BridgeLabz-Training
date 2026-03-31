
public class StringConcatenationPerformance {
    public static void main(String[] args) {
        int n = 100000; // reduced to 100k for safe execution
        int numbers[] = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i;
        }

        //testing String concatenation
        String output = "";
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            output += "a"; // inefficient
        }
        long end = System.nanoTime();
        System.out.println("String time is- " + (end - start) / 1_000_000 + " ms");

        //testing StringBuilder
        StringBuilder sb = new StringBuilder();
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            sb.append("a"); // efficient
        }
        end = System.nanoTime();
        System.out.println("StringBuilder time is- " + (end - start) / 1_000_000 + " ms");

        //testing StringBuffer
        StringBuffer sbf = new StringBuffer();
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            sbf.append("a"); // thread-safe
        }
        end = System.nanoTime();
        System.out.println("StringBuffer time is- " + (end - start) / 1_000_000 + " ms");
    }
}