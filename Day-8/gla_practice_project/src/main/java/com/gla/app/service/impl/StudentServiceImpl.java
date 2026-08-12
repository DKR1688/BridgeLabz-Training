package com.gla.app.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gla.app.entity.Student;
import com.gla.app.repository.StudentRepository;
import com.gla.app.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repository;

	public StudentServiceImpl(StudentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Student saveStudent(Student student) {

		return repository.save(student);

	}

	@Override
	public List<Student> getAllStudents() {

		return repository.findAll();

	}

	@Override
	public Student getStudentById(Integer id) {

		return findExistingStudent(id);

	}

	@Override
	public Student updateStudent(Integer id, Student student) {

		Student old = findExistingStudent(id);

			old.setRollNumber(student.getRollNumber());
			old.setFirstName(student.getFirstName());
			old.setLastName(student.getLastName());
			old.setGender(student.getGender());
			old.setEmail(student.getEmail());
			old.setAddress(student.getAddress());
			old.setCity(student.getCity());
			old.setState(student.getState());
			old.setCourse(student.getCourse());
			old.setDepartment(student.getDepartment());
			old.setAdmissionYear(student.getAdmissionYear());

			return repository.save(old);
	}

	@Override
	public void deleteStudent(Integer id) {

		repository.delete(findExistingStudent(id));

	}

	private Student findExistingStudent(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id));
	}

}
