package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Bus;
import com.example.booking_project.entity.Driver;
import com.example.booking_project.entity.Route;
import com.example.booking_project.entity.Schedule;
import com.example.booking_project.services.BusService;
import com.example.booking_project.services.DriverService;
import com.example.booking_project.services.RouteService;
import com.example.booking_project.services.ScheduleService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
class ScheduleRequest {

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
    @Autowired 
    private BusService busService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private RouteService routeService;
    

    @GetMapping
    public Page<Schedule> getAllSchedules(Pageable pageable) {
        return scheduleService.getAllSchedules(pageable);
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable("id") UUID id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        Bus bus = busService.getBusById(scheduleRequest.busId);
        Driver driver = driverService.getDriverById(scheduleRequest.driverId);
        Route route = routeService.getRouteById(scheduleRequest.routeId);
        schedule.setBus(bus);
        schedule.setDriver(driver);
        schedule.setRoute(route);
        schedule.setDepartureTime(LocalDateTime.parse(scheduleRequest.departureTime));
        schedule.setPrice(scheduleRequest.price);
        return ResponseEntity.ok(scheduleService.createSchedule(schedule));
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable("id") UUID id, @RequestBody ScheduleRequest scheduleRequest) {
        Schedule updatedSchedule = new Schedule();
        Bus bus = busService.getBusById(scheduleRequest.busId);
        Driver driver = driverService.getDriverById(scheduleRequest.driverId);
        Route route = routeService.getRouteById(scheduleRequest.routeId);
        updatedSchedule.setBus(bus);
        updatedSchedule.setDriver(driver);
        updatedSchedule.setRoute(route);
        updatedSchedule.setDepartureTime(LocalDateTime.parse(scheduleRequest.departureTime));
        updatedSchedule.setPrice(scheduleRequest.price);

        return scheduleService.updateSchedule(id, updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable("id") UUID id) {
        scheduleService.deleteSchedule(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
