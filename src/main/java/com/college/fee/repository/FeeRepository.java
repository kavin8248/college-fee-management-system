package com.college.fee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.fee.entity.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long>{
	List<Fee> findByStudent_RegisterNumber(String registerNumber);

}
