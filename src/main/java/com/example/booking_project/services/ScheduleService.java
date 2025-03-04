package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Schedule;
import com.example.booking_project.repositories.ScheduleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Page<Schedule> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

    public Optional<Schedule> getScheduleById(UUID id) {
        return scheduleRepository.findById(id);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> updateSchedule(UUID id, Schedule updatedSchedule) {
        return scheduleRepository.findById(id).map(schedule -> {
            schedule.setBus(updatedSchedule.getBus());
            schedule.setDriver(updatedSchedule.getDriver());
            schedule.setRoute(updatedSchedule.getRoute());
            schedule.setDepartureTime(updatedSchedule.getDepartureTime());
            schedule.setPrice(updatedSchedule.getPrice());
            return scheduleRepository.save(schedule);
        });
    }

    public void deleteSchedule(UUID id) {
        scheduleRepository.deleteById(id);
    }
}
