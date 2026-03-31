import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractLinksFromWebPage {
	public static void main(String[] args) {
		String text ="Visit https://www.google.com and http://example.org for more info.";
		
		String regex="https?://(www\\.)?[a-zA-Z0-9.]+\\.[a-z]{2,}";
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher =pattern.matcher(text);
		
		while(matcher.find()) {
			System.out.print(matcher.group()+", ");
		}
	}
}
