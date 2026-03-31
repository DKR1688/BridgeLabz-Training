class Employee {
    public int employeeID; 
    protected String department; 
    private double salary;
    
    Employee(int employeeID, String department, double salary) {
        this.employeeID =employeeID;
        this.department =department;
        this.salary =salary;
    }

    //public method to modify salary
    public void setSalary(double salary) {
        if (salary>0) {
            this.salary = salary;
            System.out.println("Updated salary is- " + salary);
        } else {
            System.out.println("Invalid salary amount!");
        }
    }

    //public method to get salary
    public double getSalary() {
        return salary;
    }

    public void displayEmployee() {
        System.out.println("Employee ID is- " +employeeID);
        System.out.println("Department is-  " +department);
        System.out.println("Salary is- " +salary);
        System.out.println();
    }
    
    public static void main(String[] args) {
        Manager manager= new Manager(101, "IT", 75000.0, "Development Team");

        System.out.println("Initial employee details are- ");
        manager.displayEmployee();

        // Modify salary
        manager.setSalary(80000.0);

        System.out.println("Updated employee details- ");
        manager.displayEmployee();

        System.out.println("Manager Details are- ");
        manager.displayManager();
    }
}

class Manager extends Employee {
    String managerName;

    Manager(int employeeID, String department, double salary, String managerName) {
        super(employeeID, department, salary);
        this.managerName =managerName;
    }

    //demonstrating access modifiers
    public void displayManager() {
        System.out.println("Manager employee ID is- " +employeeID);
        System.out.println("Manager department is- " +department);

        //to access private salary must use getter
        System.out.println("Manager salary is- " +getSalary());
        System.out.println("Team name is- " +managerName);
    }
}