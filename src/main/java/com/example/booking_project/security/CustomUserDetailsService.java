package com.example.booking_project.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.User;
import com.example.booking_project.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // Or user.getPhone()
                .password(user.getPassword()) // Password should be encoded
                .roles(user.getRole()) // Example: "USER", "ADMIN"
                .build();
    }
}
