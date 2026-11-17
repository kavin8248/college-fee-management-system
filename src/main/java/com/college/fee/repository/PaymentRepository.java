package com.college.fee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.fee.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
