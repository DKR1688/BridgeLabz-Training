import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SearchEmployee {

    public static void main(String[] args) {

        String filePath = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\employee.csv";
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter employee name to search: ");
        String searchName = sc.nextLine();

        boolean found = false;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String name = data[1];

                if (name.equalsIgnoreCase(searchName)) {

                    String department = data[2];
                    String salary = data[3];

                    System.out.println("Employee Found!");
                    System.out.println("Department : " + department);
                    System.out.println("Salary     : " + salary);

                    found = true;
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Employee not found!");
        }

        sc.close();
    }
}
