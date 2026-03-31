import java.util.Scanner;
import java.util.regex.*;

public class ValidateHaxColorCode {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter re- ");
		String str=sc.nextLine();
		
		String regex="^[#][A-Fa-f0-9]{6}$";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(str);
		boolean matches =matcher.matches();
		
		System.out.println(matches);
	}
}
