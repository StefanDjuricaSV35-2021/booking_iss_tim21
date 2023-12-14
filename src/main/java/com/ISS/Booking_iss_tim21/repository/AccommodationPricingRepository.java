package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationPricingRepository extends JpaRepository<AccommodationPricing, Long> {
    @Query("select a from AccommodationPricing a where a.accommodation.id = ?1")
    public List<AccommodationPricing> getAccommodationPricingsForAccommodation(Long accommodationId);
}
