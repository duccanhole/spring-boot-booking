package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Seat;
import com.example.booking_project.services.SeatService;
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
class SeatRequestDTO {

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

    @GetMapping
    public ResponseEntity<Page<Seat>> getAllSeats(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(seatService.getAllSeats(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable UUID id) {
        Optional<Seat> seat = seatService.getSeatById(id);
        return seat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seat> createSeat(@Valid @RequestBody SeatRequestDTO seatRequest) {
        Seat seat = new Seat();
//        seat.setScheduleId(seatRequest.getScheduleId());
//        seat.setSeatNumber(seatRequest.getSeatNumber());

        return ResponseEntity.ok(seatService.createSeat(seat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable UUID id, @Valid @RequestBody SeatRequestDTO seatRequest) {
        Seat updatedSeat = new Seat();
//        updatedSeat.setScheduleId(seatRequest.getScheduleId());
//        updatedSeat.setSeatNumber(seatRequest.getSeatNumber());

        return seatService.updateSeat(id, updatedSeat)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable UUID id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
