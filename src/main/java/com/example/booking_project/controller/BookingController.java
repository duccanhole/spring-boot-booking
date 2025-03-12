package com.example.booking_project.controller;

import com.example.booking_project.entity.Booking;
import com.example.booking_project.entity.Schedule;
import com.example.booking_project.entity.Seat;
import com.example.booking_project.entity.User;
import com.example.booking_project.services.BookingService;
import com.example.booking_project.services.SeatService;
import com.example.booking_project.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// DTO inside the Controller file
@Getter
@Setter
class BookingRequest {
    @JsonProperty("user_id")
    @NotNull(message = "User ID is required.")
    public UUID userId;

    @JsonProperty("seat_id")
    @NotNull(message = "Seat ID is required.")
    public UUID seatId;

    @JsonProperty("status")
    @NotBlank(message = "Status is required.")
    public String status;
}

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private SeatService seatService;

    @GetMapping
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingService.getAllBookings(pageable);
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable("id") UUID id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = new Booking();
        User user = userService.getUserById(bookingRequest.userId);
        Seat seat = seatService.getSeatById(bookingRequest.seatId);
        booking.setStatus(bookingRequest.status);
        booking.setUser(user);
        booking.setSeat(seat);
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable("id") UUID id, @RequestBody BookingRequest bookingRequest) {
    	 Booking booking = new Booking();
         User user = userService.getUserById(bookingRequest.userId);
         Seat seat = seatService.getSeatById(bookingRequest.seatId);
         booking.setStatus(bookingRequest.status);
         booking.setUser(user);
         booking.setSeat(seat);

        return bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable("id") UUID id) {
        bookingService.deleteBooking(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search-user")
    public Page<Booking> searchScheduleByRoute(Pageable pageable, @RequestParam("userId") UUID userId) {
        return bookingService.searchBookingByUser(pageable, userId);
    }
}
