package com.example.booking_project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_project.entity.User;
import com.example.booking_project.services.UserService;

@RestController  // Marks this class as a RESTful web service
@RequestMapping("/hello")  // Base path for all endpoints in this controller
public class Hello {
	 @Autowired
	 private UserService userService;
	@GetMapping
	public String home() {
		return "this is Home";
	}
	
    @GetMapping("/get-user")  // Defines a GET endpoint
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
