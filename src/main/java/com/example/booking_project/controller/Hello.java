package com.example.booking_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this class as a RESTful web service
@RequestMapping("/")  // Base path for all endpoints in this controller
public class Hello {
	@GetMapping
	public String home() {
		return "this is Home";
	}
	
    @GetMapping("/hello")  // Defines a GET endpoint
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
}
