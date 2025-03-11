package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Driver;
import com.example.booking_project.entity.User;
import com.example.booking_project.services.DriverService;
import com.example.booking_project.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
class DriverRequest {
    @JsonProperty("user_id")
    @NotNull(message = "User ID is required.")
    public String userId;

    @JsonProperty("license_number")
    @NotBlank(message = "License number is required.")
    public String licenseNumber;

    @JsonProperty("status")
    @NotBlank(message = "Status is required.")
    public String status;
}

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<Driver> getAllDrivers(Pageable pageable) {
        return driverService.getAllDrivers(pageable);
    }

    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable("id") UUID id) {
        Driver driver = driverService.getDriverById(id);
        return driver;
    }
    
    @PostMapping
    public ResponseEntity<Object> createDriver(@RequestBody DriverRequest driverRequest) {
    	Map<String, Object> response = new HashMap<>();
    	if(driverService.existsByLicenseNumber(driverRequest.licenseNumber)) {
    		response.put("message", "License number is existed");
    		return ResponseEntity.badRequest().body(response);
    	}
    	if(driverService.existsByUserId(UUID.fromString(driverRequest.userId))) {
    		response.put("message", "User id is existed");
    		return ResponseEntity.badRequest().body(response);
    	}
        Driver driver = new Driver();
        User user = userService.getUserById(UUID.fromString(driverRequest.userId));
        driver.setUser(user);
        driver.setLicenseNumber(driverRequest.licenseNumber);
        driver.setStatus(driverRequest.status);

        return ResponseEntity.ok(driverService.createDriver(driver));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDriver(@PathVariable("id") UUID id, @RequestBody DriverRequest driverRequest) {
    	Map<String, Object> response = new HashMap<>();
    	if(driverService.existsByLicenseNumber(driverRequest.licenseNumber, id)) {
    		response.put("message", "License number is existed");
    		return ResponseEntity.badRequest().body(response);
    	}
    	if(driverService.existsByUserId(UUID.fromString(driverRequest.userId), id)) {
    		response.put("message", "User id is existed");
    		return ResponseEntity.badRequest().body(response);
    	}
        Driver updatedDriver = new Driver();
        User user = userService.getUserById(UUID.fromString(driverRequest.userId));
        updatedDriver.setUser(user);
        updatedDriver.setLicenseNumber(driverRequest.licenseNumber);
        updatedDriver.setStatus(driverRequest.status);

        return ResponseEntity.ok(driverService.updateDriver(id, updatedDriver));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDriver(@PathVariable("id") UUID id) {
        driverService.deleteDriver(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
