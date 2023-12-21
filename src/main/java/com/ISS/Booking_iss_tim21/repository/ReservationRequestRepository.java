package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {
    @Query("select r from ReservationRequest r where r.user.Id = ?1")
    public List<ReservationRequest> getUsersReservationRequestsById(Long userId);

    @Query("select r from ReservationRequest r where r.accommodation.owner.Id = ?1")
    public List<ReservationRequest> getUsersReservationRequestsByOwnerId(Long userId);
}
