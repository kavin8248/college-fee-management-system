package com.college.fee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.college.fee.entity.Fee;
import com.college.fee.entity.Student;
import com.college.fee.exception.FeeNotFoundException;
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
	
	public List<Fee> getAllFees(){
		return feeRepository.findAll();
	}
	
	public List<Fee> getStudentFee(String registerNumber) {
		 if (!studentRepository.existsById(registerNumber)) {
		        throw new StudentNotFoundException("Student not found");
		    }

		return feeRepository.findByStudent_RegisterNumber(registerNumber);
	}
	
	public Fee updateFee(Long feeId, Fee updatedFee) {
		Fee existingFee = feeRepository.findById(feeId).orElseThrow(() -> new FeeNotFoundException("Fee not found"));
		
		existingFee.setFeeType(updatedFee.getFeeType());
		existingFee.setAmount(updatedFee.getAmount());
	    existingFee.setDueDate(updatedFee.getDueDate());
	    existingFee.setStatus(updatedFee.getStatus());
	    
	    return feeRepository.save(existingFee);
	}
	
	public void deleteFee(Long feeId) {
		Fee fee = feeRepository.findById(feeId).orElseThrow(() -> new FeeNotFoundException("Fee not found"));
		
		feeRepository.delete(fee);
	}

}
