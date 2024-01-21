package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview,Long> {
    @Query("select a from AccommodationReview a where a.reviewed.id = ?1")
    public List<AccommodationReview> findAllByAccommodationId(Long accommodationId);

}
