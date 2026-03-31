package com.healthclinic.dao;

import com.healthclinic.model.Patient;
import com.healthclinic.util.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class PatientDao {

	//UC-1: register new patient
	public void insertPatient(Patient patient) throws Exception {
		Connection connection=DatabaseConnection.getConnection();
		String sql = "INSERT INTO patients(name, dob, phone, email, address, blood_group) VALUES(?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, patient.getName());
		ps.setString(2, patient.getDob());
		ps.setString(3, patient.getPhone());
		ps.setString(4, patient.getEmail());
		ps.setString(5, patient.getAddress());
		ps.setString(6, patient.getBloodGroup());
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			patient.setId(rs.getInt(1));
		}
		connection.close();
	}

	//UC-2: updating patient information
	public void updatePatient(Patient patient) throws Exception {
		Connection connection =DatabaseConnection.getConnection();
		String sql = "UPDATE patients SET name=?, dob=?, phone=?, email=?, address=?, blood_group=? WHERE patient_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, patient.getName());
		ps.setString(2, patient.getDob());
		ps.setString(3, patient.getPhone());
		ps.setString(4, patient.getEmail());
		ps.setString(5, patient.getAddress());
		ps.setString(6, patient.getBloodGroup());
		ps.setInt(7, patient.getId());
		ps.executeUpdate();
		connection.close();
	}

	//UC-3: searching patient record
	public List<Patient> searchPatient(String criteria) throws Exception {
		Connection connection= DatabaseConnection.getConnection();
		String sql = "SELECT * FROM patients WHERE patient_id=? OR phone=? OR name LIKE ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, criteria);
		ps.setString(2, criteria);
		ps.setString(3, "%" + criteria + "%");
		ResultSet rs = ps.executeQuery();

		List<Patient> patients =new ArrayList<>();
		while (rs.next()) {
			Patient p = new Patient();
			p.setId(rs.getInt("patient_id"));
			p.setName(rs.getString("name"));
			p.setPhone(rs.getString("phone"));
			p.setEmail(rs.getString("email"));
			patients.add(p);
		}
		connection.close();
		return patients;
	}

	//UC-4: view patient history
	public List<String> getVisitHistory(int patientId) throws Exception {
	    Connection connection = DatabaseConnection.getConnection();
	    String sql = "SELECT a.date, v.diagnosis, d.doctor_name " +
	             "FROM appointments a " +
	             "JOIN visits v ON a.appointment_id = v.appointment_id " +
	             "JOIN doctors d ON a.doctor_id = d.doctor_id " +
	             "WHERE a.patient_id = ? " +
	             "ORDER BY a.date";
	    PreparedStatement ps = connection.prepareStatement(sql);
	    ps.setInt(1, patientId);
	    ResultSet rs = ps.executeQuery();

	    List<String> history = new ArrayList<>();
	    while (rs.next()) {
	        history.add(rs.getDate("date") + " - " +
	                    rs.getString("doctor_name") + " - " +
	                    rs.getString("diagnosis"));
	    }
	    connection.close();
	    return history;
	}
}