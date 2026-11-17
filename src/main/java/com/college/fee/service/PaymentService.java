package com.college.fee.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.college.fee.entity.Fee;
import com.college.fee.exception.FeeNotFoundException;
import com.college.fee.payment.Payment;
import com.college.fee.repository.FeeRepository;
import com.college.fee.repository.PaymentRepository;

@Service
public class PaymentService {
	private FeeRepository feeRepository;
	private PaymentRepository paymentRepository;
	public PaymentService(FeeRepository feeRepository, PaymentRepository paymentRepository) {
		super();
		this.feeRepository = feeRepository;
		this.paymentRepository = paymentRepository;
	}
	@Transactional
	public Payment makePayment(Long feeId) {
		Fee fee = feeRepository.findById(feeId).orElseThrow(() -> new FeeNotFoundException("Fee not found"));
		Payment payment = new Payment();
		fee.setStatus("PAID");
		payment.setFee(fee);
		payment.setAmount(BigDecimal.valueOf(fee.getAmount()));
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentStatus("SUCCESS");
		payment.setPaymentMethod("SIMULATED");
		return paymentRepository.save(payment);
	}
}
