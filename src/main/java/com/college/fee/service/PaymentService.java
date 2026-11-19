package com.college.fee.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.college.fee.entity.Fee;
import com.college.fee.exception.FeeNotFoundException;
import com.college.fee.payment.Payment;
import com.college.fee.payment.PaymentGateway;
import com.college.fee.payment.PaymentResult;
import com.college.fee.repository.FeeRepository;
import com.college.fee.repository.PaymentRepository;

@Service
public class PaymentService {
	private FeeRepository feeRepository;
	private PaymentRepository paymentRepository;
	private PaymentGateway paymentGateway;
	
	

	public PaymentService(FeeRepository feeRepository, PaymentRepository paymentRepository,
			PaymentGateway paymentGateway) {
		super();
		this.feeRepository = feeRepository;
		this.paymentRepository = paymentRepository;
		this.paymentGateway = paymentGateway;
	}



	@Transactional
	public Payment makePayment(Long feeId) {
		Fee fee = feeRepository.findById(feeId).orElseThrow(() -> new FeeNotFoundException("Fee not found"));
		if ("PAID".equalsIgnoreCase(fee.getStatus())) {
		    throw new IllegalArgumentException("This fee has already been paid");
		}
		System.out.println("Fee ID: " + feeId + ", Status: " + fee.getStatus());
		PaymentResult result = paymentGateway.charge(
			    BigDecimal.valueOf(fee.getAmount())
			);
		if (!result.success()) {
		    throw new IllegalArgumentException("Payment failed");
		}
		Payment payment = new Payment();
		payment.setGatewayReference(result.reference());
		fee.setStatus("PAID");
		payment.setFee(fee);
		payment.setAmount(BigDecimal.valueOf(fee.getAmount()));
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentStatus("SUCCESS");
		payment.setPaymentMethod("SIMULATED");
		return paymentRepository.save(payment);
	}
}
