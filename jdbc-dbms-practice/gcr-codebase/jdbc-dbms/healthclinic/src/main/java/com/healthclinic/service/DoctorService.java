package com.healthclinic.service;

import com.healthclinic.dao.DoctorDao;
import com.healthclinic.model.Doctor;

public class DoctorService {
	private DoctorDao dao = new DoctorDao();

	public void registerDoctor(Doctor doctor) throws Exception {
		dao.insertDoctor(doctor);
	}
}