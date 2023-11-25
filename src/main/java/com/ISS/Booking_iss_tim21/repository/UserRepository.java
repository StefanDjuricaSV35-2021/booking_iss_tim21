package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
