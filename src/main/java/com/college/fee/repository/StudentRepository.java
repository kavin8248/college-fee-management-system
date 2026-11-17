package com.college.fee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.fee.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String>{

}
