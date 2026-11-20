package com.college.fee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	public Payment makePayment(@PathVariable Long feeId,  @RequestHeader("Idempotency-Key") String idempotencyKey) {
		return paymentService.makePayment(feeId, idempotencyKey);
	}
	
	@GetMapping("/history/{registerNumber}")
	public List<Payment> getPaymentHistory(@PathVariable String registerNumber){
		return paymentService.getPaymentHistory(registerNumber);
	}
	@GetMapping("/{paymentId}")
	public Payment getByPaymentById(@PathVariable Long paymentId) {
		return paymentService.getPaymentById(paymentId);
	}
}
