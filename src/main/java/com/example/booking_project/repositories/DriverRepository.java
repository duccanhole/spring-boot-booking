package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.Driver;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Page<Driver> findAll(Pageable pageable);
    
    @Query("SELECT COUNT(b) > 0 FROM Driver b WHERE LOWER(b.licenseNumber) = LOWER(:licenseNumber) AND b.id != :id")
	boolean existsByLicenseNumber(@Param("licenseNumber") String licensePlate, @Param("id") UUID id);
    @Query("SELECT COUNT(b) > 0 FROM Driver b WHERE LOWER(b.licenseNumber) = LOWER(:licenseNumber)")
	boolean existsByLicenseNumber(@Param("licenseNumber") String licensePlate);
    
    @Query("SELECT COUNT(b) > 0 FROM Driver b WHERE b.user.id = :userId AND b.id != :id")
	boolean existsByUserId(@Param("userId") UUID userId, @Param("id") UUID id);
    @Query("SELECT COUNT(b) > 0 FROM Driver b WHERE b.user.id = :userId")
	boolean existsByUserId(@Param("userId") UUID userId);
}
