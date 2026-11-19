package com.college.fee.payment;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;
@Component
public class SimulatedPaymentGateway implements PaymentGateway{
	@Override
	public PaymentResult charge(BigDecimal amount) {
		 boolean success = amount.compareTo(new BigDecimal("100000")) <= 0;

		 return new PaymentResult(success, "SIM-" + UUID.randomUUID());
	}
}
