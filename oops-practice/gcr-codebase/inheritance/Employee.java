//Employee Management System
public class Employee {
    String name;
    int id;
    double salary;

    Employee(String name, int id, double salary) {
        this.name=name;
        this.id=id;
        this.salary=salary;
    }

    void displayDetails() {
        System.out.println("Name of employee- " +name);
        System.out.println("ID of employee- " +id);
        System.out.println("Salary of employee- " +salary);
    }
    
    public static void main(String[] args) {
        Employee manager =new Manager("Deepak", 1, 100000, 10);
        manager.displayDetails();
        
        Employee developer =new Developer("Abhay", 2, 60000, "Java");
        developer.displayDetails();
        
        Employee intern =new Intern("Rishabh", 3, 30000, "Capgemini");
        intern.displayDetails();
    }
}

// Subclasses
class Manager extends Employee {
    int teamSize;

    Manager(String name, int id, double salary, int teamSize) {
        super(name, id, salary);
        this.teamSize =teamSize;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Team size of manager- "+teamSize);
        System.out.println();
    }
}

class Developer extends Employee {
    String programmingLanguage;

    Developer(String name, int id, double salary, String programmingLanguage) {
        super(name, id, salary);
        this.programmingLanguage =programmingLanguage;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Programming language of developer- " + programmingLanguage);
        System.out.println();
    }
}

class Intern extends Employee {
    String companay;

    Intern(String name, int id, double salary, String companay) {
        super(name, id, salary);
        this.companay =companay;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Companay of intern- " +companay);
        System.out.println();
    }
}