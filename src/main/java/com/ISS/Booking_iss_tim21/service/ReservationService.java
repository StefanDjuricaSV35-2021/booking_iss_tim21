package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    public List<Reservation> getAll(){
        return repository.findAll();
    }


    public Reservation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(Reservation reservation) { repository.save(reservation); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<Reservation> getUsersReservationsById(Long userId) { return repository.getUsersReservationsById(userId); }

    public List<Reservation> getActiveUsersReservationsById(Long userId) { return repository.getActiveUsersReservationsById(userId); }

    public List<Reservation> getCurrentActiveReservationsForAccommodation(Long accommodationId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getActiveReservationsForAccommodation(accommodationId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }

    public List<Reservation> getCurrentActiveReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getActiveUsersReservationsById(userId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }
    public List<Reservation> getCurrentReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }
}
