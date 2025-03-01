package com.example.booking_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "John Doe");
        return "index"; // This refers to index.html in the templates folder
    }
}
