package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.review.Review;
import com.ISS.Booking_iss_tim21.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository repository;

    public List<Review> getAll() {return repository.findAll();}
    public Review findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void remove(Long id) { repository.deleteById(id);}
}
