package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.UserActivationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserActivationRequestRepository extends JpaRepository<UserActivationRequest,Long> {
    @Query("select u from UserActivationRequest u where u.email = ?1")
    UserActivationRequest findByEmail(String email);
}
