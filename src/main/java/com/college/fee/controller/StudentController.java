package com.college.fee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.fee.entity.Student;
import com.college.fee.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	@PostMapping
	public Student registerStudent(@RequestBody Student student) {
		return studentService.registerStudent(student);
	}
	
	@GetMapping
	public List<Student> getAllStudents(){
		return studentService.getAllStudent();
	}
	@GetMapping("/{registerNumber}")
	public Student getStudentByRegisterNumber(@PathVariable String registerNumber) {
		return studentService.getStudentByRegisterNumber(registerNumber);
	}
	@PutMapping("/{registerNumber}")
	public Student updateStudent(@PathVariable String registerNumber,@RequestBody Student student) {
		return studentService.updateStudent(registerNumber, student);
	}
	@DeleteMapping("/{registerNumber}")
	public ResponseEntity<Void> deleteStudent(
	        @PathVariable String registerNumber) {

	    studentService.deleteStudent(registerNumber);

	    return ResponseEntity.noContent().build();
	}
}
