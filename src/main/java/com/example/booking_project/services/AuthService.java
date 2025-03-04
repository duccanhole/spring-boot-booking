package com.example.booking_project.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.booking_project.entity.User;
import com.example.booking_project.repositories.UserRepository;
import com.example.booking_project.utils.JwtUtil;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public String login(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByEmailOrPhone(identifier);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
        	logger.debug("find user with username: " + user.getName());
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(user.getEmail());
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
    
    public User register(String name, String email, String phone, String password, String role) {
        // Check if email or phone already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByPhone(phone)) {
            throw new RuntimeException("Phone number already in use");
        }
        
        logger.debug("raw password: " + password);

        // Encrypt password
        String encryptedPassword = passwordEncoder.encode(password);
        
        logger.debug("encrypted password: " + encryptedPassword);

        // Create new user
        User newUser = new User(name, email, phone, encryptedPassword, role);
        return userRepository.save(newUser);
    }
}