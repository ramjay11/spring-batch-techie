package com.ramjava.spring.batch.techie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// By default, Spring Batch is synchronous
@SpringBootApplication()
public class SpringBatchTechieApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchTechieApplication.class, args);
	}

}
