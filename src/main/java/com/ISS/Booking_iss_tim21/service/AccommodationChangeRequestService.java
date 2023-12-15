package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import com.ISS.Booking_iss_tim21.repository.AccommodationChangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationChangeRequestService {
    @Autowired
    AccommodationChangeRequestRepository repository;
    public List<AccommodationChangeRequest> getAll(){
        return repository.findAll();
    }

    public List<AccommodationChangeRequest> getAllPending(){
        return repository.findAll();
    }
    public AccommodationChangeRequest findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(AccommodationChangeRequest accommodationChangeRequest) { repository.save(accommodationChangeRequest); }
    public void remove(Long id) {
        repository.deleteById(id);
    }
}