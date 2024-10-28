package com.unik.unikForma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnikFormaApplication {

	private static final Logger logger = LoggerFactory.getLogger(UnikFormaApplication.class);

	public static void main(String[] args) {
		// Start the Spring application
		SpringApplication.run(UnikFormaApplication.class, args);

		logger.info("Application has started successfully.");
	}
}
