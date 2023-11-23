package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Notification;
import com.ISS.Booking_iss_tim21.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository repository;

    public List<Notification> getAll(){
        return repository.findAll();
    }

    public Notification findOne(Integer id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }







}
