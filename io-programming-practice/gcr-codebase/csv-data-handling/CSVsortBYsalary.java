import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Employee {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
}
public class CSVsortBYsalary {

	public static void main(String[] args) {
		String path="D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\employee.csv";
		List<Employee> employees = new ArrayList<>();
		String line;
		try(BufferedReader br=new BufferedReader(new FileReader(path)))
		{
            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                Employee emp = new Employee(Integer.parseInt(data[0]),data[1],data[2],Double.parseDouble(data[3]));
                employees.add(emp);
            }
		}
		catch (Exception e) {
			System.out.println("Exception or list");
			// TODO: handle exception
		}
        Collections.sort(employees, (e1, e2) -> Double.compare(e2.salary, e1.salary));
        System.out.println("Employees sorted by salary Descending:");
        for (Employee e : employees) {
            System.out.println(e.id + ", " + e.name + ", " + e.department + ", " + e.salary);
        }
    }
}