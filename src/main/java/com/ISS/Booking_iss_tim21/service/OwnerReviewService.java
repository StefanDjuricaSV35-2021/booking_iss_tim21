package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.OwnerReview;
import com.ISS.Booking_iss_tim21.repository.OwnerReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerReviewService {

    OwnerReviewRepository repository;

    public List<OwnerReview> getAll() {return repository.findAll();}
    public OwnerReview findOne(Integer id) {
        return repository.findById(id).orElseGet(null);
    }
    public void remove(Integer id) { repository.deleteById(id);}
}