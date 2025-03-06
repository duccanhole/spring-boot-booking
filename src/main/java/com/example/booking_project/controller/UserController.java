package com.example.booking_project.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.User;
import com.example.booking_project.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
class UserRequest {
	@NotBlank(message = "Name is required.")
    @JsonProperty("name")
    public String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @JsonProperty("email")
    public String email;

    @JsonProperty("phone")
    public String phone;

    @NotBlank(message = "Password is required.")
    @JsonProperty("password")
    public String password;

    @NotBlank(message = "Role is required.")
    @JsonProperty("role")
    public String role;
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getAllUser(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
    	User user = new User(userRequest.name, userRequest.email, userRequest.phone, userRequest.password, userRequest.role);
    	return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody UserRequest userRequest) {
    	User user = new User(userRequest.name, userRequest.email, userRequest.phone, userRequest.password, userRequest.role);
    	return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
