import java.util.regex.*;
public class ValidateSocialSecurityNumber {
	public static void main(String[] args) {
        String text1="My SSN is 123-45-6789.";
        String text2="My SSN is 123456789.";

        String regex="\\b[0-9]{3}-[0-9]{2}-[0-9]{4}\\b";
        Pattern pattern=Pattern.compile(regex);

        Matcher matcher1=pattern.matcher(text1);
        if (matcher1.find()) {
            System.out.println(matcher1.group() +" is valid");
        }else {
        	String digits = text1.replaceAll("\\D+", "");
        	System.out.println(digits+" is invalid");
        }

        Matcher matcher2 =pattern.matcher(text2);
        if (matcher2.find()) {
            System.out.println(matcher2.group()+" is valid");
        }else {
        	String digits = text1.replaceAll("\\D+", "");
        	System.out.println(digits+" is invalid");
        }
    }
}
