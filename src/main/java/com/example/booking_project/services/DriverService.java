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

    public Optional<Driver> getDriverById(UUID id) {
        return driverRepository.findById(id);
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> updateDriver(UUID id, Driver updatedDriver) {
        return driverRepository.findById(id).map(driver -> {
            driver.setLicenseNumber(updatedDriver.getLicenseNumber());
            driver.setStatus(updatedDriver.getStatus());
            return driverRepository.save(driver);
        });
    }

    public void deleteDriver(UUID id) {
        driverRepository.deleteById(id);
    }
}
