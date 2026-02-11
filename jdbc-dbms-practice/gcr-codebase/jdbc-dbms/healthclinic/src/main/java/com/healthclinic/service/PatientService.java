package com.healthclinic.service;

import com.healthclinic.dao.PatientDao;
import com.healthclinic.exception.DuplicatePatientException;
import com.healthclinic.model.Patient;
import java.util.List;

public class PatientService {
	private PatientDao dao=new PatientDao();

	//UC-1 
	public void registerPatient(Patient patient) throws Exception {
		List<Patient> existing = dao.searchPatient(patient.getPhone());
		if (!existing.isEmpty()) {
			throw new DuplicatePatientException("Patient with phone already exists!");
		}
		dao.insertPatient(patient);
	}

	//UC-2
	public void updatePatient(Patient patient) throws Exception {
		dao.updatePatient(patient);
	}

	//UC-3
	public List<Patient> searchPatient(String criteria) throws Exception {
		return dao.searchPatient(criteria);
	}

	//UC-4
	public List<String> getVisitHistory(int patientId) throws Exception {
		return dao.getVisitHistory(patientId);
	}
}