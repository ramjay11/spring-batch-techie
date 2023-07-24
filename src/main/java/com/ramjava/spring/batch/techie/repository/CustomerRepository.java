package com.ramjava.spring.batch.techie.repository;

import com.ramjava.spring.batch.techie.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
