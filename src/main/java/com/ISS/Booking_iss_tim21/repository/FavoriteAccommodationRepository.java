package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.FavoriteAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteAccommodationRepository extends JpaRepository<FavoriteAccommodation,Long> {
}
