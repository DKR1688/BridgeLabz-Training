import java.io.*;
public class CsvWriter {
	public static void main(String[] args) {
        String filePath="D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\csv-data-handling\\employee.csv";

        try (PrintWriter writer =new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,Name,Department,Salary");

            //writing employee records
            writer.println("1,Deepak,IT,50000");
            writer.println("2,Rajput,HR,45000");
            writer.println("3,Abahy,Finance,60000");

            System.out.println("CSV file created successfully- "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
