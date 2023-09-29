package com.ramjava.spring.batch.techie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
//    @Column(name = "age")
//    private String age;
}
