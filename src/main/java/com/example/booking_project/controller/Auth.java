package com.example.booking_project.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_project.dto.SignUpRequest;
import com.example.booking_project.entity.User;
import com.example.booking_project.services.AuthService;
import com.example.booking_project.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

class SignInRequest {
	@JsonProperty("username")
	public String username;
	
    @JsonProperty("password")
    public String password;
}

@RestController
@RequestMapping("/auth")
public class Auth {
	private static final Logger logger = LoggerFactory.getLogger(Auth.class);
    private final AuthService authService;
    private final UserService userService;

    public Auth(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(@RequestBody SignInRequest request) {
    	logger.debug(String.format("Login: username - %s, password - %s", request.username, request.password));
        String token = authService.login(request.username, request.password);
        Optional<User> user = userService.getUserByPhoneOrEmai(request.username);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user.get());
        return response;
    }
    
    @PostMapping("/sign-up")
    public Map<String, Object> signup(
    		@RequestBody SignUpRequest request) {
    	
    	logger.debug(String.format("Sign up: name - %s, phone - %s, password - %s, role - %s", request.name, request.email, request.phone, request.password, request.role));
    	
        User newUser = authService.register(request.name, request.email, request.phone, request.password, request.role);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", newUser);
        return response;
    }
}