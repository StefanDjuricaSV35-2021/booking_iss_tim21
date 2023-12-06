package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email = ?1")
    public User findByEmail(String email);
}
