package com.example.booking_project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	 Optional<User> findByEmail(String email);
	 Optional<User> findByPhone(String phone);
	 Optional<User> findById(UUID id);
	 @Query("SELECT u FROM User u WHERE u.email = :query OR u.phone = :query")
	 Optional<User> findByEmailOrPhone(@Param("query") String query);
	 @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.email) = LOWER(:email)")
	 boolean existsByEmail(@Param("email") String email);
	 @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.phone) = LOWER(:phone)")
	 boolean existsByPhone(@Param("phone") String phone);
}
