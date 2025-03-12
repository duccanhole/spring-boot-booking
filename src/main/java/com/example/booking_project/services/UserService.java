package com.example.booking_project.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.User;
import com.example.booking_project.repositories.UserRepository;
import com.example.booking_project.utils.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(JwtUtil jwtUtil) {
    	this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }
    	
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Page<User> getAllUsers(Pageable pageable){
    	return userRepository.findAll(pageable);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user found."));
    }
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public User updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
//            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User updatePassword(UUID id, String password) {
    	// Encrypt password
        String encryptedPassword = passwordEncoder.encode(password);
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setPassword(encryptedPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    
    public Optional<User> getUserByPhoneOrEmai(String identifier){
    	return userRepository.findByEmailOrPhone(identifier);
    }
    
    public Boolean isExistByEmail(String email) {
    	return userRepository.existsByEmail(email);
    }
    
    public Boolean isExistByPhone(String phone) {
    	return userRepository.existsByPhone(phone);
    }
    
    public Boolean isExistByEmail(String email, UUID id) {
    	return userRepository.existsByEmail(email, id);
    }
    
    public Boolean isExistByPhone(String phone, UUID id) {
    	return userRepository.existsByPhone(phone, id);
    }
}
