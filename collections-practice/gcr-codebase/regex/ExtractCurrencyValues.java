import java.util.regex.*;

public class ExtractCurrencyValues {
	public static void main(String[] args) {
		String text="The price is $45.99, and the discount is 10.50.";
		
		String regex="\\$[0-9]+(\\.[0-9]+)?|[0-9]+(\\.[0-9]+)?";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(text);
		
		while(matcher.find()) {
			System.out.print(matcher.group()+", ");
		}
	}

}
