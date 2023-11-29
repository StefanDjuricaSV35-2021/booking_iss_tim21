package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.OwnerReview;
import com.ISS.Booking_iss_tim21.repository.OwnerReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerReviewService {

    @Autowired

    OwnerReviewRepository repository;

    public List<OwnerReview> getAll() {return repository.findAll();}
    public OwnerReview findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public List<OwnerReview> getAllByOwnerId(Long id){return repository.findAllByOwnerId(id);}
    public void remove(Long id) { repository.deleteById(id);}
    public void save(OwnerReview or){repository.save(or);}
}
