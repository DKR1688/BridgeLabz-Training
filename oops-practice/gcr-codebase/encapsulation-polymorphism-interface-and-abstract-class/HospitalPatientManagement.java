
public class HospitalPatientManagement {
	public static void main(String[] args) {
		Patient p1 = new InPatient(1, "Deepak", 45, 1000, 5);
		Patient p2 = new OutPatient(2, "Abahy", 30, 2000);

		p1.getPatientDetails();
		System.out.println("Bill is- " + p1.calculateBill());
		((InPatient) p1).addRecord("Admitted for surgery");
		((InPatient) p1).viewRecords();

		System.out.println();
		p2.getPatientDetails();
		System.out.println("Bill is- " + p2.calculateBill());
	}
}

//Abstract
abstract class Patient {
	private int patientId;
	private String name;
	private int age;

	public Patient(int patientId, String name, int age) {
		this.patientId = patientId;
		this.name = name;
		this.age = age;
	}

	public void getPatientDetails() {
		System.out.println("ID is- " + patientId + "\n Name is- " + name + "\n Age is- " + age);
	}

	public abstract double calculateBill();
}

//Interface
interface MedicalRecord {
	void addRecord(String record);

	void viewRecords();
}

//InPatient
class InPatient extends Patient implements MedicalRecord {
	private double dailyCharge;
	private int days;
	private String record;

	public InPatient(int patientId, String name, int age, double dailyCharge, int days) {
		super(patientId, name, age);
		this.dailyCharge = dailyCharge;
		this.days = days;
	}

	@Override
	public double calculateBill() {
		return dailyCharge * days;
	}

	@Override
	public void addRecord(String record) {
		this.record = record;
	}

	@Override
	public void viewRecords() {
		System.out.println("Medical Record is- " + record);
	}
}

//OutPatient
class OutPatient extends Patient {
	private double consultationFee;

	public OutPatient(int patientId, String name, int age, double consultationFee) {
		super(patientId, name, age);
		this.consultationFee = consultationFee;
	}

	@Override
	public double calculateBill() {
		return consultationFee;
	}
}
