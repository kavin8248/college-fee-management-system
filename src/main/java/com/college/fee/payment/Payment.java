package com.college.fee.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.college.fee.entity.Fee;

import jakarta.persistence.Entity;
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
	private String paymentStatus;
	private String paymentMethod;
	private String gatewayReference;
	public String getGatewayReference() {
		return gatewayReference;
	}


	public void setGatewayReference(String gatewayReference) {
		this.gatewayReference = gatewayReference;
	}


	@ManyToOne
	@JoinColumn(name = "fee_id",nullable = false)
	private Fee fee;
	
	
	public Payment() {
	}


	public Payment(Long paymentID, BigDecimal amount, LocalDateTime paymentDate, String paymentStatus,
			String paymentMethods, Fee fee) {
		super();
		this.paymentID = paymentID;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethods;
		this.fee = fee;
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


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethods) {
		this.paymentMethod = paymentMethods;
	}


	public Fee getFee() {
		return fee;
	}


	public void setFee(Fee fee) {
		this.fee = fee;
	}
	
	
}
