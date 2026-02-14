package com.healthclinic.dao;

import com.healthclinic.model.Doctor;
import com.healthclinic.util.DatabaseConnection;
import java.sql.*;

public class DoctorDao {

	//UC-2.1 Inserting new doctor profile
	public void insertDoctor(Doctor doctor) throws Exception {
		String sql = "INSERT INTO doctors(doctor_name, specialization) VALUES(?,?)";
		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

		    ps.setString(1, doctor.getName());
		    ps.setString(2, doctor.getSpecialization());
		    ps.executeUpdate();

		    ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        doctor.setId(rs.getInt(1));
		    }
		}
	}
}