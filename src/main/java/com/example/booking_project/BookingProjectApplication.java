package com.example.booking_project;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class BookingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingProjectApplication.class, args);
	}
}
