import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ExtractAllEmail {
	public static void main(String[] args) {
		String text ="Contact us at support@example.com and info@company.org";
		
		String regex="[a-zA-Z0-9.$-+%]+@[a-zA-Z0-9.]+\\.[a-z]{2,}";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(text);
		
		while(matcher.find()) {
			System.out.println("Email is- "+matcher.group());
		}
	}
}
