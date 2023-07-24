package com.ramjava.spring.batch.techie.config;

import com.ramjava.spring.batch.techie.entity.Customer;
import com.ramjava.spring.batch.techie.repository.CustomerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerItemWriter implements ItemWriter<Customer> {
    @Autowired
    CustomerRepository repository;
//    @Override
//    public void write(List<? extends Customer> list) throws Exception {
//        System.out.println("Writer thread " + Thread.currentThread().getName());
//        repository.saveAll(list);
//    }

    @Override
    public void write(Chunk<? extends Customer> list) throws Exception {
        System.out.println("Writer thread " + Thread.currentThread().getName());
        repository.saveAll(list);
    }
}
