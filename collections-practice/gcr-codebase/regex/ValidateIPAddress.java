import java.util.*;
import java.util.regex.*;
public class ValidateIPAddress {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your IP address- ");
		String IP =sc.nextLine();
		
		String regex="^^((25[0-5] | 2[0-4][0-9] | [01]?[0-9][0-9]?)\\.){3}\r\n" +"(25[0-5] | 2[0-4][0-9] | [01]?[0-9][0-9]?)$";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(IP);
		boolean matches=matcher.matches();
		System.out.println(IP+" - "+matches);
	}
}
