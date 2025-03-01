package com.example.booking_project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email); // Fetch user by email
}
