package com.example.lease_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaseManagementApplication.class, args);
	}


@Bean
public CommandLineRunner runApp() {
	return (args -> {
		System.out.println("hello");
	});

}}
