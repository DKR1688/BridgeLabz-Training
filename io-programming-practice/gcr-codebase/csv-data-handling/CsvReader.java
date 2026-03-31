import java.io.*;
public class CsvReader {
	public static void main(String[] args) {
        String filePath ="D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\csv-data-handling\\student.csv";

        try (BufferedReader br=new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line =br.readLine())!=null) {
                String[] data = line.split(",");

                String id =data[0];
                String name =data[1];
                String age =data[2];
                String marks =data[3];
                
                System.out.println("ID- " +id +", Name- " +name+ 
                                   ", Age- " +age +", Marks- " +marks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
