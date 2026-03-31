import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class ValidateUsers {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =Pattern.compile("^[6-9][0-9]{9}$");

    public static void main(String[] args) {

        String filePath = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\users.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String id = data[0];
                String email = data[2];
                String phone = data[3];

                boolean valid = true;

                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    System.out.println("Invalid Email at ID " + id + " : " + email);
                    valid = false;
                }

                if (!PHONE_PATTERN.matcher(phone).matches()) {
                    System.out.println("Invalid Phone at ID " + id + " : " + phone);
                    valid = false;
                }

                if (!valid) {
                    System.out.println("❌ Invalid Row → " + line);
                    System.out.println("---------------------------");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
