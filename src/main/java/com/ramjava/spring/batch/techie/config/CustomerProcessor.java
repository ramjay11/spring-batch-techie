package com.ramjava.spring.batch.techie.config;

import com.ramjava.spring.batch.techie.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

// Read item as Customer, write it as Customer <Customer, Customer>
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        // Filter customers who came from US
        /*
        if (customer.getCountry().equals("United States")) {
            return customer;
        } else {
            return null;
        }
         */
        // Customer age greater than 18
        int age = Integer.parseInt(customer.getAge());
        if (age >= 18) {
            return customer;
        }
        return null;
    }
}
