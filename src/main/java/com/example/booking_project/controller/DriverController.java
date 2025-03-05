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
    public Driver createDriver(@RequestBody DriverRequest driverRequest) {
        Driver driver = new Driver();
        User user = userService.getUserById(UUID.fromString(driverRequest.userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        driver.setUser(user);
        driver.setLicenseNumber(driverRequest.licenseNumber);
        driver.setStatus(driverRequest.status);

        return driverService.createDriver(driver);
    }
    
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable("id") UUID id, @RequestBody DriverRequest driverRequest) {
        Driver updatedDriver = new Driver();
        User user = userService.getUserById(UUID.fromString(driverRequest.userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        updatedDriver.setUser(user);
        updatedDriver.setLicenseNumber(driverRequest.licenseNumber);
        updatedDriver.setStatus(driverRequest.status);

        return driverService.updateDriver(id, updatedDriver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok().build();
    }
}
