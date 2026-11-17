package com.college.fee.service;

import org.springframework.stereotype.Service;

import com.college.fee.entity.Fee;
import com.college.fee.entity.Student;
import com.college.fee.exception.StudentNotFoundException;
import com.college.fee.repository.FeeRepository;
import com.college.fee.repository.StudentRepository;

@Service
public class FeeService {
	private final FeeRepository feeRepository;
	private final StudentRepository studentRepository;
	public FeeService(FeeRepository feeRepository, StudentRepository studentRepository) {
		super();
		this.feeRepository = feeRepository;
		this.studentRepository = studentRepository;
	}

	public Fee assignFee(Fee fee) {
		String registerNumber = fee.getStudent().getRegisterNumber();
		Student student = studentRepository.findById(registerNumber).orElseThrow(() -> new StudentNotFoundException("Student not found"));
		fee.setStudent(student);
		return feeRepository.save(fee);
	}

}
