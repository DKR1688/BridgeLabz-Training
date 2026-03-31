package strings;

public class RemoveASpecificCharacter {
	public static void main(String[] args) {
        String string ="Hello World";
        char charToRemove = 'l';

        String newString = removeCharacter(string, charToRemove);
        System.out.println("Modified String: " +newString);
    }
	
	// a method to remove a specific character from string
	public static String removeCharacter(String str, char ch) {
		
        StringBuilder sb= new StringBuilder();
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) != ch) {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}
