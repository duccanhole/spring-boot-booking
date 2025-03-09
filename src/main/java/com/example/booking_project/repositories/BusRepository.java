package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.Bus;

import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {
    Page<Bus> findAll(Pageable pageable);
    
    @Query("SELECT COUNT(b) > 0 FROM Bus b WHERE LOWER(b.licensePlate) = LOWER(:licensePlate) AND b.id != :id")
	boolean existsByLicensePlate(@Param("licensePlate") String licensePlate, @Param("id") UUID id);
    @Query("SELECT COUNT(b) > 0 FROM Bus b WHERE LOWER(b.licensePlate) = LOWER(:licensePlate)")
	boolean existsByLicensePlate(@Param("licensePlate") String licensePlate);
}
