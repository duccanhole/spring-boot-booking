package com.example.booking_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Page {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "John Doe");
        return "index"; // This refers to index.html in the templates folder
    }
    
    @GetMapping("/sign-in")
    public String signIn() {
    	return "sign-in";
    }
    
    @GetMapping("/sign-up")
    public String signUp() {
    	return "sign-up";
    }
}
