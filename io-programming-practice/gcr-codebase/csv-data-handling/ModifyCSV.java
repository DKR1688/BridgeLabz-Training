import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyCSV {

    public static void main(String[] args) {

        String inputFile = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\employee.csv";
        String outputFile = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\updated_employee.csv";

        String line;

        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {

            // Read and write header
            bw.write(br.readLine());
            bw.newLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String department = data[2];
                double salary = Double.parseDouble(data[3]);

                // Increase salary by 10% for IT department
                if (department.equalsIgnoreCase("IT")) {
                    salary = salary * 1.10;
                }

                // Write updated record
                bw.write(id + "," + name + "," + department + "," + salary);
                bw.newLine();
            }

            System.out.println("Salary updated and saved to new CSV file!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
