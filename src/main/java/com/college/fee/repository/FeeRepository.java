package com.college.fee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.college.fee.entity.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long>{
	

}
