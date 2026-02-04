import java.util.Arrays;
import java.util.List;

public class WelcomeMessage {
    public static void main(String[] args) {

        List<String> attendees = Arrays.asList("Abhay","Radhika","Arjun","Anuj","Jyotika");
        attendees.forEach(attendee -> System.out.println("Welcome to the event, " + attendee + "!"));
    }
}
