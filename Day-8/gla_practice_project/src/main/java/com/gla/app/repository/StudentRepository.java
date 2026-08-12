package com.gla.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gla.app.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
