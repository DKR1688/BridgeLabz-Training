package HospitalManagementSystem;

public class Patient extends Person {
    String disease;

    public Patient(int id, String name, int age, String disease) {
        super(id, name, age);
        this.disease = disease;
    }

    @Override
    public void showDetails() {
        System.out.println("Patient name is- " + name + "\n Age is- " + age + "\n Disease is- " + disease);
    }

    public String getDisease() {
        return disease;
    }
}
