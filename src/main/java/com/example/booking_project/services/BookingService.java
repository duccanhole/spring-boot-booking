package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Booking;
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

    public Optional<Booking> getBookingById(UUID id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> updateBooking(UUID id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
//            booking.setUserId(updatedBooking.getUserId());
//            booking.setSeatId(updatedBooking.getSeatId());
            booking.setStatus(updatedBooking.getStatus());
            return bookingRepository.save(booking);
        });
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }
}
