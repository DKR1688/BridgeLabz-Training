import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Merge2CSV {

    public static void main(String[] args) throws Exception {

        Map<Integer, String[]> map = new HashMap<>();

        BufferedReader br1 = new BufferedReader(new FileReader("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\student1.csv"));
        br1.readLine();
        String line;

        while ((line = br1.readLine()) != null) {
            String[] data = line.split(",");
            map.put(Integer.parseInt(data[0]), data);
        }
        br1.close();

        BufferedReader br2 = new BufferedReader(new FileReader("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\student2.csv"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\merged.csv"));

        bw.write("ID,Name,Age,Marks,Grade\n");
        br2.readLine();

        while ((line = br2.readLine()) != null) {
            String[] d2 = line.split(",");
            int id = Integer.parseInt(d2[0]);

            if (map.containsKey(id)) {
                String[] d1 = map.get(id);
                bw.write(id + "," + d1[1] + "," + d1[2] + "," + d2[1] + "," + d2[2]);
                bw.newLine();
            }
        }

        br2.close();
        bw.close();

        System.out.println("CSV files merged successfully!");
    }
}
