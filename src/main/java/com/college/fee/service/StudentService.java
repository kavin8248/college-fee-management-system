package com.college.fee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.college.fee.entity.Student;
import com.college.fee.exception.StudentNotFoundException;
import com.college.fee.repository.StudentRepository;

@Service
public class StudentService {
	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}
	
	
	public Student registerStudent(Student student) {
		if(studentRepository.existsById(student.getRegisterNumber())) {
			throw new StudentNotFoundException("Student already exists");
		}
		
		return studentRepository.save(student);
	}
	
	public List<Student> getAllStudent(){
		return studentRepository.findAll();
	}
	
	public Student getStudentByRegisterNumber(String registerNumber) {
		return studentRepository.findById(registerNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
	}
	
	public Student updateStudent(String registerNumber,Student updatedStudent) {
		Student existingStudent = studentRepository.findById(registerNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
		
		existingStudent.setName(updatedStudent.getName());
		existingStudent.setDepartment(updatedStudent.getDepartment());
		existingStudent.setEmail(updatedStudent.getEmail());
		existingStudent.setPhone(updatedStudent.getPhone());
		
		return studentRepository.save(existingStudent);
	}
	
	public void deleteStudent(String registerNumber) {
		Student student = studentRepository.findById(registerNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
		studentRepository.delete(student);
	}
	
}
