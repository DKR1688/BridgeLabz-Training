//Employee Management System
class Employee {
    //static variable shared by all employees
    static String companyName ="Tech Solutions Inc.";
    static int totalEmployees =0;
     
    final int id;
    String name;
    String designation;

    Employee(int id, String name, String designation) {
        this.id =id;
        this.name =name;
        this.designation =designation;
        totalEmployees++;
    }
    
    //static method to display total employees
    static void displayTotalEmployees() {
        System.out.println("Total employees are- " +totalEmployees);
    }

    public void displayEmployeeDetails(Object object) {
        if (object instanceof Employee) {
            System.out.println("Company name is- " +companyName);
            System.out.println("Employee ID is- " +id);
            System.out.println("Name is- " +name);
            System.out.println("Designation is- " +designation);
            System.out.println();
        } else {
            System.out.println("Not a valid Employee object.");
        }
    }
    
    public static void main(String[] args) {
        Employee employee1 =new Employee(101, "Thamarai", "Software Engineer");
        Employee employee2 =new Employee(102, "Rohan", "Project Manager");

        Employee.displayTotalEmployees();

        //displaying employee details using instanceof
        employee1.displayEmployeeDetails(employee1);
        employee2.displayEmployeeDetails(employee2);
    }
}