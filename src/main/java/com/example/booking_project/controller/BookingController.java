package com.example.booking_project.controller;

import com.example.booking_project.entity.Booking;
import com.example.booking_project.services.BookingService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

// DTO inside the Controller file
@Getter
@Setter
class BookingRequestDTO {
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

    @GetMapping
    public ResponseEntity<Page<Booking>> getAllBookings(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookingService.getAllBookings(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO bookingRequest) {
        Booking booking = new Booking();
        booking.setStatus(bookingRequest.status);

        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID id, @RequestBody BookingRequestDTO bookingRequest) {
        Booking updatedBooking = new Booking();
        updatedBooking.setStatus(bookingRequest.status);

        return bookingService.updateBooking(id, updatedBooking)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
