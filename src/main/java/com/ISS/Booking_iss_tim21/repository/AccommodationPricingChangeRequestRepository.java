package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.AccommodationPricingChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationPricingChangeRequestRepository extends JpaRepository<AccommodationPricingChangeRequest, Long> {

    @Query("select a from AccommodationPricingChangeRequest a where a.accommodationChangeRequest.id = ?1 and a.status = com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus.PENDING")
    public List<AccommodationPricingChangeRequest> findAllForAccommodation(Long accommodationChangeRequestId);
}
