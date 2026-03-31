import java.util.*;
public class FlipKeyLogical {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the word- ");
		String str=sc.nextLine();
		
		String key=CleanseAndInvert(str);
		if(key.isEmpty()) {
			System.out.println("Your input is invalid.");
		}else {
			System.out.println("The generated key is- "+key);
		}
	}
	
	public static String CleanseAndInvert(String str) {
		if(str==null || str.length()<6) {
			return "";
		}
		
		//key only allow alphabets
		if(!str.matches("[a-zA-Z]+")){
			return "";
		}
		
		//here we convert str to lowercase and remove characters with even ascii values
		str=str.toLowerCase();
		StringBuilder sb=new StringBuilder();
		for(char ch: str.toCharArray()) {
			if((int) ch%2!=0) {
				sb.append(ch);
			}
		}
		
		//here we reverse sb and convert even position chars to uppercase
		sb.reverse();
		StringBuilder ans=new StringBuilder();
		for(int i=0; i<sb.length(); i++) {
			char ch=sb.charAt(i);
			if(i%2==0) {
				ans.append(Character.toUpperCase(ch));
			}else {
				ans.append(ch);
			}
		}
		return ans.toString();
	}
}
