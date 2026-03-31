import java.io.BufferedReader;
import java.io.FileReader;

public class FilterRecord {
	public static void main(String[] args) {
        String filePath = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\student.csv";
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                int marks = Integer.parseInt(data[3]);
                if(marks>80)
                {
                	int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                System.out.println("Student ID   : " + id);
                System.out.println("Name         : " + name);
                System.out.println("Age          : " + age);
                System.out.println("Marks        : " + marks);
                System.out.println("---------------------------");
            }

	}
        }
        catch (Exception e) {
			// TODO: handle exception
		}
}
}
