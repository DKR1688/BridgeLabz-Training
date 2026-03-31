import java.util.regex.*;
import java.util.Scanner;
public class ValidateUsername {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter re- ");
		String str=sc.nextLine();
		
		String regex="^[a-zA-Z][a-zA-Z0-9_]{4,14}$";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(str);
		boolean matches =matcher.matches();
		
		System.out.println(matches);
	}
}
