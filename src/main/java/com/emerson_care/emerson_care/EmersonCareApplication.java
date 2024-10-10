package com.emerson_care.emerson_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.emerson_care.emerson_care"})
public class EmersonCareApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmersonCareApplication.class, args);
	}
}
