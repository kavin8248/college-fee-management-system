package com.college.fee.controller;

import org.springframework.web.bind.annotation.RestController;

import com.college.fee.entity.Fee;
import com.college.fee.service.FeeService;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}