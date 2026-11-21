package com.college.fee.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.college.fee.entity.Fee;
import com.college.fee.exception.FeeNotFoundException;
import com.college.fee.exception.PaymentNotFoundException;
import com.college.fee.exception.StudentNotFoundException;
import com.college.fee.payment.Payment;
import com.college.fee.payment.PaymentGateway;
import com.college.fee.payment.PaymentResult;
import com.college.fee.payment.PaymentStatus;
import com.college.fee.repository.FeeRepository;
import com.college.fee.repository.PaymentRepository;
import com.college.fee.repository.StudentRepository;

@Service
public class PaymentService {
	private FeeRepository feeRepository;
	private PaymentRepository paymentRepository;
	private PaymentGateway paymentGateway;
	private StudentRepository studentRepository;
	private PaymentRecordService paymentRecordService;
	
	

	public PaymentService(FeeRepository feeRepository, PaymentRepository paymentRepository,
			PaymentGateway paymentGateway, StudentRepository studentRepository,
			PaymentRecordService paymentRecordService) {
		super();
		this.feeRepository = feeRepository;
		this.paymentRepository = paymentRepository;
		this.paymentGateway = paymentGateway;
		this.studentRepository = studentRepository;
		this.paymentRecordService = paymentRecordService;
	}

	public Payment makePayment(Long feeId, String idempotencyKey) {

	    Payment existingPayment = paymentRepository
	            .findByIdempotencyKey(idempotencyKey)
	            .orElse(null);

	    if (existingPayment != null) {
	        if (!existingPayment.getFee().getFeeId().equals(feeId)) {
	            throw new IllegalArgumentException(
	                    "Idempotency key already used for another fee"
	            );
	        }
	        return existingPayment;
	    }

	    Fee fee = feeRepository.findById(feeId)
	            .orElseThrow(() -> new FeeNotFoundException("Fee not found"));

	    if ("PAID".equalsIgnoreCase(fee.getStatus())) {
	        throw new IllegalArgumentException("This fee has already been paid");
	    }

	    // 1. Create payment record
	    Payment payment = new Payment();

	    payment.setIdempotencyKey(idempotencyKey);
	    payment.setFee(fee);
	    payment.setAmount(BigDecimal.valueOf(fee.getAmount()));
	    payment.setPaymentDate(LocalDateTime.now());
	    payment.setPaymentStatus(PaymentStatus.PENDING);
	    payment.setPaymentMethod("SIMULATED");

	    // 2. Save PENDING payment
	    payment = paymentRecordService.savePendingPayment(payment);

	    // 3. Contact payment gateway
	    PaymentResult result = paymentGateway.charge(payment.getAmount());
	 // 4. Check gateway result
	    PaymentStatus status = result.success()
	            ? PaymentStatus.SUCCESS
	            : PaymentStatus.FAILED;

	    payment = paymentRecordService.updatePaymentResult(
	            payment.getPaymentID(),
	            status,
	            result.reference()
	    );

	    return payment;
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
