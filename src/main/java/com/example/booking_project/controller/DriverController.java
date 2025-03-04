package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Driver;
import com.example.booking_project.services.DriverService;
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
class DriverRequestDTO {

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

    @GetMapping
    public ResponseEntity<Page<Driver>> getAllDrivers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(driverService.getAllDrivers(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable UUID id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        return driver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody DriverRequestDTO driverRequest) {
        Driver driver = new Driver();
        driver.setUserId(driverRequest.userId);
        driver.setLicenseNumber(driverRequest.licenseNumber);
        driver.setStatus(driverRequest.status);

        return ResponseEntity.ok(driverService.createDriver(driver));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable UUID id, @Valid @RequestBody DriverRequestDTO driverRequest) {
        Driver updatedDriver = new Driver();
        updatedDriver.setUserId(driverRequest.userId);
        updatedDriver.setLicenseNumber(driverRequest.licenseNumber);
        updatedDriver.setStatus(driverRequest.status);

        return driverService.updateDriver(id, updatedDriver)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
