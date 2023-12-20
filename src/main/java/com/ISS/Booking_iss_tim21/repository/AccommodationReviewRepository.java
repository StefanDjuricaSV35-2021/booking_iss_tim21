package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview,Long> {
}
