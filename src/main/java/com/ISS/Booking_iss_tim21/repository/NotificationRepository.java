package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select n from Notification n where n.recipient.Id = ?1")
    public List<Notification> findAllByUserId(Long userId);

}
