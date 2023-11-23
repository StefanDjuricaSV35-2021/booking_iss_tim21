package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.repository.AccommodationReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationReviewService {

    @Autowired
    AccommodationReviewRepository repository;

    public List<AccommodationReview> getAll() {return repository.findAll();}
    public AccommodationReview findOne(Integer id) {
        return repository.findById(id).orElseGet(null);
    }
    public void remove(Integer id) { repository.deleteById(id);}
}
