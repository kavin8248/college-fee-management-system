package com.college.fee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	@Id
	private String registerNumber;
	
	private String name;
	private String department;
	private String email;
	private String phone;
	
	
	

	public Student(String registerNumber, String name, String department, String email, String phone) {
		super();
		this.registerNumber = registerNumber;
		this.name = name;
		this.department = department;
		this.email = email;
		this.phone = phone;
	}

	public Student() {
	}
	
	public String getRegisterNumber() {
		return registerNumber;
	}


	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


}
