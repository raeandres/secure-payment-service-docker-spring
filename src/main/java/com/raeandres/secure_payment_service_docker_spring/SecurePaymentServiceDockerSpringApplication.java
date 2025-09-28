package com.raeandres.secure_payment_service_docker_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SecurePaymentServiceDockerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurePaymentServiceDockerSpringApplication.class, args);
	}

}
