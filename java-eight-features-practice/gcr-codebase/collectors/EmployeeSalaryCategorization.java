
import java.util.*;
import java.util.stream.Collectors;
public class EmployeeSalaryCategorization {
	public static void main(String[] args) {
        List<Employee> employees =Arrays.asList(
            new Employee("Deepak", "HR", 50000),
            new Employee("Abhishek", "IT", 60000),
            new Employee("Abhay", "HR", 55000),
            new Employee("Ganni", "Finance", 70000)
        );

        //grouping employees by department and calculate average salary
        Map<String, Double> avgSalaryByDept =employees.stream().collect(Collectors.groupingBy(
                Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)
            ));

        avgSalaryByDept.forEach((dept, avgSalary) -> System.out.println(dept+ " - "+avgSalary));
    }
}

class Employee {
    private String name;
    private String department;
    private double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }
}
