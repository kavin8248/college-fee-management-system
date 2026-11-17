package com.college.fee.controller;

import org.springframework.web.bind.annotation.RestController;

import com.college.fee.entity.Fee;
import com.college.fee.service.FeeService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/fees")
public class FeeController {
	private final FeeService feeService;
	
	public FeeController(FeeService feeService) {
		this.feeService = feeService;
	}
	
	@PostMapping
	public Fee assignFee(@RequestBody Fee fee) {
		return feeService.assignFee(fee);
	}
	@GetMapping
	public List<Fee> getAllfees(){
		return feeService.getAllFees();
	}
	@GetMapping("/student/{registerNumber}")
	public List<Fee> getFeesByStudent(@PathVariable String registerNumber){
		return feeService.getStudentFee(registerNumber);
	}
	@PutMapping("/{feeId}")
	public Fee updateFee(@PathVariable Long feeId,@RequestBody Fee fee) {
		return feeService.updateFee(feeId, fee);
	}
	@DeleteMapping("/{feeId}")
	public ResponseEntity<Void> deleteFee(@PathVariable Long feeId){
		feeService.deleteFee(feeId);
		return ResponseEntity.noContent().build();
	}
}