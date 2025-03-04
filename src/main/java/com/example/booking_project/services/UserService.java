package com.example.booking_project.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.User;
import com.example.booking_project.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByPhoneOrEmai(String identifier){
    	return userRepository.findByEmailOrPhone(identifier);
    }
}
