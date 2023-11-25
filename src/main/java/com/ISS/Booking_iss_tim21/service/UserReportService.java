package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Notification;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.repository.NotificationRepository;
import com.ISS.Booking_iss_tim21.repository.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReportService {

    @Autowired

    UserReportRepository repository;

    public List<UserReport> getAll(){
        return repository.findAll();
    }

    public UserReport findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }


}
