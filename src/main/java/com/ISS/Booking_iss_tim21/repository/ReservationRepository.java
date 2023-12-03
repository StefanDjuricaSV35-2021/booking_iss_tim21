package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.userId = ?1")
    public List<Reservation> getUsersReservationsById(Long userId);
}
