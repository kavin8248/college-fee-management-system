package com.college.fee.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.fee.payment.Payment;
import com.college.fee.repository.PaymentRepository;
import com.college.fee.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	private PaymentService paymentService;
	

	
	public PaymentController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}

	@PostMapping("/{feeId}")
	public Payment makePayment(@PathVariable Long feeId) {
		return paymentService.makePayment(feeId);
	}
}
