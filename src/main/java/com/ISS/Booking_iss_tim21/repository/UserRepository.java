package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
