package com.college.fee.payment;

import java.math.BigDecimal;

public interface PaymentGateway {
	PaymentResult charge(BigDecimal amount);
}
