package com.gla.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.gla.app.entity.Student;
import com.gla.app.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	private final StudentService service;

	public StudentController(StudentService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Student save(@Valid @RequestBody Student student) {

		return service.saveStudent(student);

	}

	@GetMapping
	public List<Student> getAll() {

		return service.getAllStudents();

	}

	@GetMapping("/{id}")
	public Student getById(@PathVariable Integer id) {

		return service.getStudentById(id);

	}

	@PutMapping("/{id}")
	public Student update(@PathVariable Integer id, @Valid @RequestBody Student student) {

		return service.updateStudent(id, student);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {

		service.deleteStudent(id);

	}

}
