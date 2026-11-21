package com.college.fee.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.college.fee.entity.Fee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentID;
	private BigDecimal amount;
	private LocalDateTime paymentDate;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private String paymentMethod;
	private String gatewayReference;
	@Column(name = "idempotency_key", unique = true)
	private String idempotencyKey;
	
	@ManyToOne
	@JoinColumn(name = "fee_id",nullable = false)
	private Fee fee;
	
	
	public Payment() {
	}





	public Payment(Long paymentID, BigDecimal amount, LocalDateTime paymentDate, PaymentStatus paymentStatus,
			String paymentMethod, String gatewayReference, String idempotencyKey, Fee fee) {
		super();
		this.paymentID = paymentID;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.gatewayReference = gatewayReference;
		this.idempotencyKey = idempotencyKey;
		this.fee = fee;
	}











	public String getIdempotencyKey() {
		return idempotencyKey;
	}


	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}


	public String getGatewayReference() {
		return gatewayReference;
	}


	public void setGatewayReference(String gatewayReference) {
		this.gatewayReference = gatewayReference;
	}


	public Long getPaymentID() {
		return paymentID;
	}


	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}



	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}





	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}





	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public Fee getFee() {
		return fee;
	}


	public void setFee(Fee fee) {
		this.fee = fee;
	}
	
	
}
