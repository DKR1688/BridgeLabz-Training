public class RomanToInteger {
    public int romanToInt(String s) {
        int values[] = new int[256];
        values['I'] = 1; 
        values['V'] = 5; 
        values['X'] = 10;
        values['L'] = 50; 
        values['C'] = 100; 
        values['D'] = 500; 
        values['M'] = 1000;

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            
            // If current value is greater than previous, subtract twice the previous
            if (i > 0 && values[s.charAt(i)] > values[s.charAt(i - 1)]) {
                total += values[s.charAt(i)] - 2 * values[s.charAt(i - 1)];
            } else {
                total += values[s.charAt(i)];
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RomanToInteger romanToInteger = new RomanToInteger();
        
        // here are some test cases
        System.out.println(romanToInteger.romanToInt("III"));
        System.out.println(romanToInteger.romanToInt("IV"));
        System.out.println(romanToInteger.romanToInt("IX"));
    }
}