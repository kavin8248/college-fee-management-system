package com.college.fee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.college.fee.exception.PaymentNotFoundException;
import com.college.fee.payment.Payment;
import com.college.fee.payment.PaymentStatus;
import com.college.fee.repository.PaymentRepository;

@Service
public class PaymentRecordService {

    private final PaymentRepository paymentRepository;


    
    public PaymentRecordService(PaymentRepository paymentRepository) {
		super();
		this.paymentRepository = paymentRepository;
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public Payment savePendingPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Payment updatePaymentResult(
	        Long paymentId,
	        PaymentStatus status,
	        String gatewayReference) {

	    Payment payment = paymentRepository.findById(paymentId)
	            .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

	    payment.setPaymentStatus(status);
	    payment.setGatewayReference(gatewayReference);

	    if (status == PaymentStatus.SUCCESS) {
	        payment.getFee().setStatus("PAID");
	    }

	    return payment;
	}
}
