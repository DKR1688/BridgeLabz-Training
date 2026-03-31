//CRUD features to hospital
import java.util.ArrayList;

public class HospitalPatientManagement {
	static ArrayList<Patient> patients= new ArrayList<>();
	
	//adding patient on hospital
	static void addPatient(Patient p) {
		patients.add(p);
		System.out.println("Patient admitted in hospital.");
	}
	
	//reading or showing patients of hospital
	static void readPatient() {
		for(Patient p: patients) {
			p.dispalyPatient();
			if(p instanceof IPayable) {
				((IPayable) p).calculateBill();
			}
		}
	}
	
	//updating patient in hospital by id
	static void updatePatient(int id, String newName) {
		for(Patient p: patients) {
			if(p.id==id) {
				p.name =newName;
				System.out.println("Patient is updated.");
				return;
			}
		}
		System.out.print("Patient not found in hospital.");
	}
	
	//deleting patient from hospital
	static void deletePatient(int id) {
		boolean removed = patients.removeIf(p -> p.id == id);
	    if (removed) {
	        System.out.println("Patient deleted.");
	    } else {
	        System.out.println("Patient not found.");
	    }
	}
	
	public static void main(String[] args) {
		Doctor d =new Doctor("Deepak", 1, "Dentist");
		
		addPatient(new InPatient("Abahy", 11, 23, 500, 3));
		addPatient(new OutPatient("Mangal", 21, 22, 300));
		System.out.println();
		
		System.out.println("All patients are- ");
		readPatient();
		System.out.println();
		
		System.out.println("Updated patient is- ");
		updatePatient(11, "Bhaadu");
		readPatient();
		System.out.println();
		
		System.out.println("Deleted patient is- ");
		deletePatient(21);
		readPatient();
		System.out.println();
		
		System.out.println("Doctor is- ");
		d.displayDoctor();
	}
}

class Patient{
	String name;
	int id;
	int age;
	
	Patient(String name, int id, int age){
		this.name =name;
		this.id =id;
		this.age =age;
	}
	
	void dispalyPatient() {
	}
}

interface IPayable{
	abstract void calculateBill();
}

class InPatient extends Patient implements IPayable{
	int rate;
	int days;
	
	InPatient(String name, int id, int age, int rate, int days){
		super(name, id, age);
		this.rate =rate;
		this.days =days;
	}
	
	@Override
	public void dispalyPatient() {
		System.out.println("Name of InPatient- "+name+ "\n Age of InPatient- "+age+ "\n Days of InPatient- "+days);
	}

	@Override
	public void calculateBill() {
		System.out.println("Bill of InPatient is- "+rate*days);
	}
}

class OutPatient extends Patient implements IPayable{
	int fee;
	
	OutPatient(String name, int id, int age, int fee){
		super(name, id, age);
		this.fee =fee;
	}
	
	@Override
	public void dispalyPatient() {
		System.out.println("Name of OutPatient- "+name+ "\n Age of OutPatient- "+age);
	}
	
	@Override
	public void calculateBill() {
		System.out.println("Fee of OutPatient- "+fee);
	}
}

class Doctor{
	String name;
	int id;
	String branch;
	
	Doctor(String name, int id, String branch){
		this.name =name;
		this.id =id;
		this.branch =branch;
	}
	
	void displayDoctor() {
		System.out.println("Name of doctor- "+name+ "Branch of doctor- "+branch);
	}
}