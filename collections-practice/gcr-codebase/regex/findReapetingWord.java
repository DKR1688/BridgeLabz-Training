import java.util.regex.*;
public class findReapetingWord {
	public static void main(String[] args) {
		String text="This is is a repeated repeated word test.";

        String regex ="\\b(\\w+)\\b\\s+\\1\\b";
        Pattern pattern=Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
	}
}
