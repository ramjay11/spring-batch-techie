package com.ramjava.spring.batch.techie.repository;

import com.ramjava.spring.batch.techie.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
