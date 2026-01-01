//Hospital Management System
public class Patient {
	//A static variable hospitalName shared among all patients.
	static String hospitalName ="City Hospital";
	static int totalPatients =0;
	
	//Use a final variable patientID to uniquely identify each patient.
	String name;
	int age;
	String ailment;
	final String patientID;
	
	//Use this to initialize name, age, and ailment in the constructor.
	Patient(String name, int age, String ailment, String patientID){
		this.name =name;
		this.age =age;
		this.ailment =ailment;
		this.patientID =patientID;
		totalPatients++;
	}
	
	//A static method getTotalPatients() to count the total patients admitted.
	static void getTotalPatients() {
		System.out.println("Total Patients are- "+totalPatients);
	}
	
	//Check if an object is an instance of the Patient class before displaying its details.
	void displayPatientDetails(Object object) {
		if(object instanceof Patient) {
			System.out.println("Hospital name is- "+hospitalName);
			System.out.println("Patient ID is- "+patientID);
			System.out.println("Name is- "+name);
			System.out.println("Age is- "+age);
			System.out.println("Ailment is- "+ailment);
			System.out.println();
		}else {
			System.out.println("Invalid Paitent details!");
		}
	}
	
	public static void main(String[] args) {
		Patient patient1 =new Patient("Lathika", 30, "Flu", "P001");
		Patient patient2 =new Patient("Lidiya", 45, "Fracture", "P002");
		
		Patient.getTotalPatients();
		
		patient1.displayPatientDetails(patient1);
		patient2.displayPatientDetails(patient2);
		
	}

}
