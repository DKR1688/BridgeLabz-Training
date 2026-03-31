import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateLicencePlateNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter re- ");
		String str=sc.nextLine();
		
		String regex="^[a-bA-B]{2}[0-9]{4}$";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(str);
		boolean matches =matcher.matches();
		
		System.out.println(matches);
	}
}
