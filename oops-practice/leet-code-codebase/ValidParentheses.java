public class ValidParentheses{
	public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([])"));
        System.out.println(isValid("([)]"));
    }
	//this method is to check valid parentheses
    public static boolean isValid(String str) {
        //Using a char array to store
        char[] arr =new char[str.length()];
        int top =-1; // stack pointer
        
        for (char c : str.toCharArray()) {
            // Push opening brackets
            if (c == '(' || c == '{' || c == '[') {
                arr[++top] = c;
            } else {
                // If stack empty
                if (top == -1) {
                	return false;
                }
                
                char open =arr[top--]; //popping
                
                //check matching pairs
                if ((c==')' && open!='(') || (c=='}' && open!='{') || (c==']' && open!='[')) {
                    return false;
                }
            }
        }
        
        //valid only if stack is empty
        return top == -1;
    }
}