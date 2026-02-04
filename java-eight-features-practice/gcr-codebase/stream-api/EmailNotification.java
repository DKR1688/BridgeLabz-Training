import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class EmailNotification {
    public static void main(String[] args) {

        List<String> emails = Arrays.asList(
                "abc@gmail.com",
                "def@gmail.com",
                "rajputji@gla.ac.in",
                "abhishek_cs22@gla.ac.in",
                "abhay.singh5_cs22@gla.a.c.in"
        );

        emails.forEach(email -> sendEmailNotification(email));
    }

    //method to send email notification
    public static void sendEmailNotification(String email) {
    	String reg = "^[a-zA-Z][a-zA-Z0-9._+-]*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$";
    	boolean match=email.matches(reg);
    	if(match)System.out.println("Email notification sent to- " + email);
    }
    
}
