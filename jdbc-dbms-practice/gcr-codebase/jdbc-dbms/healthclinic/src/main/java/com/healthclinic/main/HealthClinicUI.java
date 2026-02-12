package com.healthclinic.main;

import com.healthclinic.model.Doctor;
import com.healthclinic.model.Patient;
import com.healthclinic.service.DoctorService;
import com.healthclinic.service.PatientService;
import com.healthclinic.exception.DuplicatePatientException;
import java.util.*;

public class HealthClinicUI {
	private PatientService service=new PatientService();
	private Scanner sc=new Scanner(System.in);

	public void start() {
		while (true) {
			System.out.println("\nHealth Clinic Menu-----");
			System.out.println("1- Register New Patient");
			System.out.println("2- Update Patient Information");
			System.out.println("3- Search Patient Records");
			System.out.println("4- View Patient Visit History");
			System.out.println("5- Register New Doctor");
			System.out.println("6- Exit");
			System.out.print("Choose option- ");
			int choice=sc.nextInt();
			sc.nextLine();

			try {
				switch (choice) {
				case 1:
					registerPatient();
					break;
				case 2:
					updatePatient();
					break;
				case 3:
					searchPatient();
					break;
				case 4:
					viewHistory();
					break;
				case 5:
					registerDoctor();
					break;
				case 6:
					System.exit(0);
				default:
					System.out.println("Invalid choice!");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	//UC-1
	private void registerPatient() throws Exception {
		System.out.print("Name- ");
		String name = sc.nextLine();
		System.out.print("DOB- ");
		String dob = sc.nextLine();
		System.out.print("Phone- ");
		String phone = sc.nextLine();
		System.out.print("Email- ");
		String email = sc.nextLine();
		System.out.print("Address- ");
		String address = sc.nextLine();
		System.out.print("Blood Group- ");
		String bloodGroup = sc.nextLine();

		Patient p=new Patient(name, dob, phone, email, address, bloodGroup);
		try {
			service.registerPatient(p);
			System.out.println("Patient registered with ID- "+p.getId());
		} catch (DuplicatePatientException e) {
			System.out.println("Duplicate- "+e.getMessage());
		}
	}

	//UC-2
	private void updatePatient() throws Exception {
		System.out.print("Enter patient ID- ");
		int id = sc.nextInt();
		sc.nextLine();
		List<Patient> patients =service.searchPatient(String.valueOf(id));
		if (patients.isEmpty()) {
			System.out.println("Patient not found.");
			return;
		}
		Patient p=patients.get(0);

		System.out.println("Current details- "+p);
		System.out.println("You can leave blank to keep previous details...");
		System.out.print("New name- ");
		String name = sc.nextLine();
		if (!name.isEmpty())
			p.setName(name);

		System.out.print("New DOB- ");
		String dob = sc.nextLine();
		if (!dob.isEmpty())
			p.setDob(dob);

		System.out.print("New phone- ");
		String phone = sc.nextLine();
		if (!phone.isEmpty())
			p.setPhone(phone);

		System.out.print("New email- ");
		String email = sc.nextLine();
		if (!email.isEmpty())
			p.setEmail(email);

		System.out.print("New address- ");
		String address = sc.nextLine();
		if (!address.isEmpty())
			p.setAddress(address);

		System.out.print("New blood group- ");
		String bloodGroup = sc.nextLine();
		if (!bloodGroup.isEmpty())
			p.setBloodGroup(bloodGroup);

		service.updatePatient(p);
		System.out.println("Patient updated successfully!");
	}

	//UC-3
	private void searchPatient() throws Exception {
		System.out.print("Enter search criteria (ID/Phone/Name)- ");
		String criteria = sc.nextLine();
		List<Patient> patients =service.searchPatient(criteria);
		if (patients.isEmpty()) {
			System.out.println("No patients found!");
		} else {
			for (Patient p : patients) {
				System.out.println(p);
			}
		}
	}

	//UC-4
	private void viewHistory() throws Exception {
		System.out.print("Enter Patient ID: ");
		int id = sc.nextInt();
		sc.nextLine();
		List<String> history = service.getVisitHistory(id);
		if (history.isEmpty()) {
			System.out.println("No visit history found!");
		} else {
			System.out.println("Visit History- ");
			for (String record : history) {
				System.out.println(record);
			}
		}
	}
	
	//UC-5
	private void registerDoctor() throws Exception {
	    System.out.print("Name- ");
	    String name = sc.nextLine();
	    System.out.print("Specialization- ");
	    String specialization = sc.nextLine();
	    System.out.print("Contact- ");
	    String contact = sc.nextLine();
	    System.out.print("Consultation Fee- ");
	    double fee = sc.nextDouble();
	    sc.nextLine();

	    Doctor doctor = new Doctor(name, specialization, contact, fee);
	    DoctorService service = new DoctorService();
	    service.registerDoctor(doctor);
	    System.out.println("Doctor registered with ID- " + doctor.getId());
	}
}