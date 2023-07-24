package com.ramjava.spring.batch.techie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Map CSV to object
@Entity
@Table(name = "customer_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "customer_id")
    private int id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "country")
    private String country;
    @Column(name = "dob")
    private String dob;
    @Column(name = "age")
    private String age;
}
