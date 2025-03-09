package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Driver;
import com.example.booking_project.repositories.DriverRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Page<Driver> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

    public Driver getDriverById(UUID id) {
        return driverRepository.findById(id).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(UUID id, Driver updatedDriver) {
        return driverRepository.findById(id).map(driver -> {
        	driver.setUser(updatedDriver.getUser());
            driver.setLicenseNumber(updatedDriver.getLicenseNumber());
            driver.setStatus(updatedDriver.getStatus());
            return driverRepository.save(driver);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public void deleteDriver(UUID id) {
        driverRepository.deleteById(id);
    }
    
    public Boolean existsByLicenseNumber(String licenseNumber, UUID id) {
    	return driverRepository.existsByLicenseNumber(licenseNumber, id);
    }
    public Boolean existsByLicenseNumber(String licenseNumber) {
    	return driverRepository.existsByLicenseNumber(licenseNumber);
    }
}
