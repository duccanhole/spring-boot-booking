package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.booking_project.entity.Booking;
import com.example.booking_project.entity.Schedule;
import com.example.booking_project.repositories.BookingRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("No Booking found."));
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(UUID id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
        	booking.setUser(updatedBooking.getUser());
        	booking.setSeat(updatedBooking.getSeat());
            booking.setStatus(updatedBooking.getStatus());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("No booking found"));
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }
    
    public Page<Booking> searchBookingByUser(Pageable pageable, UUID userId) {
        return bookingRepository.searchBookingByUser(pageable, userId);
    }
}
