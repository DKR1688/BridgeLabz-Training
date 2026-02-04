import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

class GYM {
    private String name;
    String issueDate;
    String expireDate;

    public GYM(String name, String issueDate, String expireDate) {
        this.name = name;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public String getExpireDate() {
        return expireDate;
    }

    @Override
    public String toString() {
        return name + " membership will expire on " + expireDate;
    }
}

public class GymMembership {
    public static void main(String[] args) {

        List<GYM> gym = Arrays.asList(
            new GYM("Abhay", "16-05-2026", "16-05-2026"),
            new GYM("Deepak", "13-07-2025", "15-09-2025"),
            new GYM("Abhishek", "16-05-2022", "28-05-2022"),
            new GYM("Arjun", "1-02-2026", "16-02-2026"),
            new GYM("Radhika", "16-12-2026", "26-05-2027"),
            new GYM("Anuj", "16-05-2026", "16-06-2026"),
            new GYM("ABCDEF", "19-08-2027", "19-09-2027")
        );

        System.out.println("Gym members whose membership expires within next 30 days:");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        gym.stream()
           //convert String-- LocalDate & filter
           .filter(g -> {LocalDate expiry =
                       LocalDate.parse(g.getExpireDate(), formatter);

               return (!expiry.isBefore(today)) &&
                      (!expiry.isAfter(next30Days));
           })
           .forEach(System.out::println);
    }
}
