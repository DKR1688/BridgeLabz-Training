package com.gla.app.service;

import java.util.*;

import com.gla.app.entity.Student;

public interface StudentService {
	Student saveStudent(Student student);

	List<Student> getAllStudents();

	Student getStudentById(Integer id);

	Student updateStudent(Integer id, Student student);

	void deleteStudent(Integer id);
}
