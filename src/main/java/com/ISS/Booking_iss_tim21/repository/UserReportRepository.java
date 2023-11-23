package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.model.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserReportRepository extends JpaRepository<UserReport,Integer> {
}
