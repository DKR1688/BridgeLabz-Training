package HospitalManagementSystem;

public class Doctor extends Person {
    private String specialization;
    private double consultationFee;

    public Doctor(int id, String name, int age, String specialization, double consultationFee) {
        super(id, name, age);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    @Override
    public void showDetails() {
        System.out.println("Doctor name is- " + name + "\n Specialization- " + specialization + 
        				"\n Fee is- " +consultationFee);
    }
    //fee calculation
    public double calculateFee() {
        return consultationFee;
    }
}