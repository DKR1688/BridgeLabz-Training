package MapInterface;
import java.util.*;
public class GroupObjectsByProperty {
	public static void main(String[] args) {
        List<Employee> employees =Arrays.asList(new Employee("Deepak", "HR"),
        										new Employee("Abhay", "IT"),
        										new Employee("Abhishek", "HR"));

        //grouping employees by department
        Map<String, List<Employee>> grouped=new HashMap<>();

        for (Employee emp:employees) {
            grouped.computeIfAbsent(emp.department, k -> new ArrayList<>()).add(emp);
        }

        for (Map.Entry<String, List<Employee>> entry: grouped.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
    }
}
class Employee {
    String name;
    String department;

    Employee(String name, String department) {
        this.name=name;
        this.department=department;
    }

    @Override
    public String toString() {
        return name;
    }
}
