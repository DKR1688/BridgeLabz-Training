import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class DetectDuplicate {

    public static void main(String[] args) throws Exception {

        Set<String> ids = new HashSet<>();

        BufferedReader br = new BufferedReader(new FileReader("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\users.csv"));

        br.readLine(); // skip header
        String line;

        while ((line = br.readLine()) != null) {
            String id = line.split(",")[0];

            if (!ids.add(id)) {
                System.out.println("Duplicate Record Found → " + line);
            }
        }

        br.close();
    }
}
