package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Schedule;
import com.example.booking_project.entity.Seat;
import com.example.booking_project.services.ScheduleService;
import com.example.booking_project.services.SeatService;
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
class SeatRequest {

    @JsonProperty("schedule_id")
    @NotNull(message = "Schedule ID is required.")
    public UUID scheduleId;

    @JsonProperty("seat_number")
    @NotBlank(message = "Seat number is required.")
    public String seatNumber;
}

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Page<Seat> getAllSeats(Pageable pageable) {
        return seatService.getAllSeats(pageable);
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable("id") UUID id) {
        return seatService.getSeatById(id);
    }

    @PostMapping
    public Seat createSeat(@RequestBody SeatRequest seatRequest) {
        Seat seat = new Seat();
        Schedule schedule = scheduleService.getScheduleById(seatRequest.scheduleId);
        seat.setSchedule(schedule);
        seat.setSeatNumber(seatRequest.seatNumber);

        return seatService.createSeat(seat);
    }

    @PutMapping("/{id}")
    public Seat updateSeat(@PathVariable("id") UUID id, @Valid @RequestBody SeatRequest seatRequest) {
    	 Seat seat = new Seat();
         Schedule schedule = scheduleService.getScheduleById(seatRequest.scheduleId);
         seat.setSchedule(schedule);
         seat.setSeatNumber(seatRequest.seatNumber);

        return seatService.updateSeat(id, seat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSeat(@PathVariable("id") UUID id) {
        seatService.deleteSeat(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}
