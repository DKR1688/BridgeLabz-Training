import java.io.*;
import java.util.*;
class Employee implements Serializable {
    int id;
    String name;
    String department;
    double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id=id;
        this.name=name;
        this.department=department;
        this.salary=salary;
    }

    @Override
    public String toString() {
        return "Employee details- \n ID- " +id+ "\n Name-" +name +
               "\n Department- " + department+ "\n Salary- " + salary;
    }
}

public class Serialization {
	public static void main(String[] args) {
        String filename ="employees.txt";

        List<Employee> employees=new ArrayList<>();
        employees.add(new Employee(1, "Deepak", "HR", 50000));
        employees.add(new Employee(2, "Abhay", "IT", 60000));
        employees.add(new Employee(3, "Rajput", "Finance", 55000));

        //serializing employees into file
        try (ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            System.out.println("Employees have been serialized and saved to "+filename);
        } catch (IOException e) {
            System.out.println("IOException occurred while saving- "+e.getMessage());
        }

        //deserializing employees from file
        try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filename))) {
        	
            List<Employee> deserializedEmployees = (List<Employee>) ois.readObject();
            System.out.println("\nEmployees retrieved from file- ");
            for (Employee emp:deserializedEmployees) {
                System.out.println(emp);
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while reading- "+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException- "+e.getMessage());
        }
    }


}
