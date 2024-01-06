package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import jdk.jshell.Snippet;
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

    public List<Reservation> getAccommodationReservations(Long id){return repository.getReservationByAccommodationId(id);}

    public List<Reservation> getActiveAccommodationReservations(Long id){return repository.getReservationByAccommodationIdAndStatus(id, ReservationStatus.Active);}

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
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }
    public List<Reservation> getCurrentReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }

    public List<Reservation> getCurrentOwnersReservationsById(Long ownerId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getOwnersReservationsById(ownerId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }

    public List<Reservation> getFinishedUsersReservationsForOwner(Long userId, Long ownerId){
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getStatus().equals(ReservationStatus.Finished) && r.getAccommodation().getOwner().getId().equals(ownerId))
                reservations.add(r);
        }
        return reservations;
    }

    public void updateReservations(){
        long currentUnixTimestamp = System.currentTimeMillis();
        List<Reservation> allReservations = repository.findAll();
        for (Reservation r : allReservations) {
            System.out.println(r.getStatus());
            System.out.println(currentUnixTimestamp);
            System.out.println(r.getTimeSlot().getEndDate());
            if(r.getTimeSlot().getEndDate() <= currentUnixTimestamp && r.getStatus().equals(ReservationStatus.Active)){
                r.setStatus(ReservationStatus.Finished);
                repository.save(r);
            }
        }
    }
}
