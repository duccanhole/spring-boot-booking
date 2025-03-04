package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Schedule;
import com.example.booking_project.services.ScheduleService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
class ScheduleRequestDTO {

    @JsonProperty("bus_id")
    @NotNull(message = "Bus ID is required.")
    public UUID busId;

    @JsonProperty("driver_id")
    @NotNull(message = "Driver ID is required.")
    public UUID driverId;

    @JsonProperty("route_id")
    @NotNull(message = "Route ID is required.")
    public UUID routeId;

    @JsonProperty("departure_time")
    @NotNull(message = "Departure time is required.")
    public String departureTime; // Format: YYYY-MM-DD HH:mm:ss

    @JsonProperty("price")
    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be a positive value.")
    public BigDecimal price;
}

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Page<Schedule>> getAllSchedules(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(scheduleService.getAllSchedules(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable UUID id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody ScheduleRequestDTO scheduleRequest) {
        Schedule schedule = new Schedule();
//        schedule.setBusId(scheduleRequest.getBusId());
//        schedule.setDriverId(scheduleRequest.getDriverId());
//        schedule.setRouteId(scheduleRequest.getRouteId());
//        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
//        schedule.setPrice(scheduleRequest.getPrice());

        return ResponseEntity.ok(scheduleService.createSchedule(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable UUID id, @Valid @RequestBody ScheduleRequestDTO scheduleRequest) {
        Schedule updatedSchedule = new Schedule();
//        updatedSchedule.setBusId(scheduleRequest.getBusId());
//        updatedSchedule.setDriverId(scheduleRequest.getDriverId());
//        updatedSchedule.setRouteId(scheduleRequest.getRouteId());
//        updatedSchedule.setDepartureTime(scheduleRequest.getDepartureTime());
//        updatedSchedule.setPrice(scheduleRequest.getPrice());

        return scheduleService.updateSchedule(id, updatedSchedule)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
