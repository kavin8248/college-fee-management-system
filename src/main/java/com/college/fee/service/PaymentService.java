package com.college.fee.service;

import com.college.fee.payment.SimulatedPaymentGateway;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.college.fee.entity.Fee;
import com.college.fee.exception.FeeNotFoundException;
import com.college.fee.exception.PaymentNotFoundException;
import com.college.fee.exception.StudentNotFoundException;
import com.college.fee.payment.Payment;
import com.college.fee.payment.PaymentGateway;
import com.college.fee.payment.PaymentResult;
import com.college.fee.repository.FeeRepository;
import com.college.fee.repository.PaymentRepository;
import com.college.fee.repository.StudentRepository;

@Service
public class PaymentService {
	private final SimulatedPaymentGateway simulatedPaymentGateway;
	private FeeRepository feeRepository;
	private PaymentRepository paymentRepository;
	private PaymentGateway paymentGateway;
	private StudentRepository studentRepository;
	
	public PaymentService(FeeRepository feeRepository, PaymentRepository paymentRepository,
			PaymentGateway paymentGateway, StudentRepository studentRepository, SimulatedPaymentGateway simulatedPaymentGateway) {
		super();
		this.feeRepository = feeRepository;
		this.paymentRepository = paymentRepository;
		this.paymentGateway = paymentGateway;
		this.studentRepository = studentRepository;
		this.simulatedPaymentGateway = simulatedPaymentGateway;
	}

	@Transactional
	public Payment makePayment(Long feeId, String idempotencyKey) {
		Payment existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey).orElse(null);

		if (existingPayment != null) {
			if (!existingPayment.getFee().getFeeId().equals(feeId)) {
		        throw new IllegalArgumentException(
		                "Idempotency key already used for another fee"
		        );
		    }
			return existingPayment;
		}
		Fee fee = feeRepository.findById(feeId).orElseThrow(() -> new FeeNotFoundException("Fee not found"));
		if ("PAID".equalsIgnoreCase(fee.getStatus())) {
			throw new IllegalArgumentException("This fee has already been paid");
		}
		PaymentResult result = paymentGateway.charge(BigDecimal.valueOf(fee.getAmount()));
		if (!result.success()) {
			throw new IllegalArgumentException("Payment failed");
		}
		Payment payment = new Payment();
		payment.setGatewayReference(result.reference());
		payment.setIdempotencyKey(idempotencyKey);
		fee.setStatus("PAID");
		payment.setFee(fee);
		payment.setAmount(BigDecimal.valueOf(fee.getAmount()));
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentStatus("SUCCESS");
		payment.setPaymentMethod("SIMULATED");
		return paymentRepository.save(payment);
	}
	
	public List<Payment> getPaymentHistory(String registerNumber){
		if(!studentRepository.existsById(registerNumber)) {
			throw new StudentNotFoundException("Student not found");
		}
		return paymentRepository.findByFee_Student_RegisterNumber(registerNumber);
	}
	
	public Payment getPaymentById(Long paymentId) {
	    return paymentRepository.findById(paymentId)
	            .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
	}
}
