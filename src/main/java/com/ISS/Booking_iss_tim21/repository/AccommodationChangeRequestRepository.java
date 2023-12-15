package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationChangeRequestRepository extends JpaRepository<AccommodationChangeRequest, Long> {
    @Query("select a from AccommodationChangeRequest a where a.status = com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus.PENDING")
    public List<AccommodationChangeRequest> getAllPending();
}
