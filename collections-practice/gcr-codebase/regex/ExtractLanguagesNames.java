import java.util.regex.*;
public class ExtractLanguagesNames {
	public static void main(String[] args) {
		String text="I love Java, Python, and JavaScript, but I haven't tried Go yet.";
		
		String regex="\\b(C|Java|Python|Go|JavaScript|HTML|Ruby)\\b";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(text);
		
		while(matcher.find()) {
			System.out.print(matcher.group()+", ");
		}
	}
}
