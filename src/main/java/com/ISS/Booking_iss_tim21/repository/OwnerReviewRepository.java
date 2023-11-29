package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OwnerReviewRepository extends JpaRepository<OwnerReview,Long> {

    @Query("select o from OwnerReview o where o.reviewed.Id = ?1")
    public List<OwnerReview> findAllByOwnerId(Long userId);
}
