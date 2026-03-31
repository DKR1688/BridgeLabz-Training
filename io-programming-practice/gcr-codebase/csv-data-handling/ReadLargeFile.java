import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadLargeFile {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new FileReader("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\merged.csv"));

        br.readLine(); // skip header

        List<String> chunk = new ArrayList<>(100);
        String line;
        int totalCount = 0;
        while ((line = br.readLine()) != null) {
            chunk.add(line);

            if (chunk.size() == 100) {
                totalCount += chunk.size();
                System.out.println("Processed records: " + totalCount);
                chunk.clear();
            }
        }

        totalCount += chunk.size();
        System.out.println("Total records processed: " + totalCount);

        br.close();
    }
}
