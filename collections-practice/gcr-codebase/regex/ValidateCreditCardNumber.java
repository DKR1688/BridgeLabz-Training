import java.util.*;
import java.util.regex.*;
public class ValidateCreditCardNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your credit card number- ");
		String number =sc.nextLine();
		
		String regex ="^4[0-9]{15}|5[0-9]{15}$";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(number);
		boolean matches=matcher.matches();
		
		System.out.println(matches);
	}
}
