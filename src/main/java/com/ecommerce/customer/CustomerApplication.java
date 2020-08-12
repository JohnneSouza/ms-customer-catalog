package com.ecommerce.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class CustomerApplication {

	static {
		BlockHound.install();
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
