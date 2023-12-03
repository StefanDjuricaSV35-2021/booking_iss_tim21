package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("select a from Accommodation a where a.ownerId = ?1")
    public List<Accommodation> getOwnersAccommodations(Long ownerId);
}