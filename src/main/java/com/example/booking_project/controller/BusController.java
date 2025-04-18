package com.example.booking_project.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Bus;
import com.example.booking_project.services.BusService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
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
class BusRequest {
	@JsonProperty("license_plate")
    @NotBlank(message = "License plate is required.")
    String licensePlate;
    
    @JsonProperty("seat_capacity")
    @NotNull(message = "Seat capacity is required.")
    @Min(value = 1, message = "Seat capacity must be at least 1.")
    Integer seatCapacity;
    
    @JsonProperty("type")
    @NotBlank(message = "Type is required.")
    String type;  // Should be "Limousine" or "Sleeper"
    
    @JsonProperty("status")
    @NotBlank(message = "Status is required.")
    String status;
}

@RestController
@RequestMapping("/api/buses")
public class BusController {
    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public Page<Bus> getAllBuses(Pageable pageable) {
        return busService.getAllBuses(pageable);
    }

    @GetMapping("/{id}")
    public Bus getBusById(@PathVariable("id") UUID id) {
        return busService.getBusById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createBus(@RequestBody BusRequest busRequest) {
    	Map<String, Object> response = new HashMap<>();
    	if(busService.existsByLicensePlate(busRequest.licensePlate)) {
    		response.put("message", "Biển số đã tồn tại");
    		return ResponseEntity.badRequest().body(response);
    	}
    	Bus bus = new Bus(busRequest.licensePlate, busRequest.seatCapacity, busRequest.type, busRequest.status);
        return ResponseEntity.ok(busService.createBus(bus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBus(@PathVariable("id") UUID id, @RequestBody BusRequest busRequest) {
    	Map<String, Object> response = new HashMap<>();
    	if(busService.existsByLicensePlate(busRequest.licensePlate, id)) {
    		response.put("message", "Biển số đã tồn tại");
    		return ResponseEntity.badRequest().body(response);
    	}
    	Bus bus = new Bus(busRequest.licensePlate, busRequest.seatCapacity, busRequest.type, busRequest.status);
        return  ResponseEntity.ok(busService.updateBus(id, bus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBus(@PathVariable("id") UUID id) {
        busService.deleteBus(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
