package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.AccommodationPricingChangeRequest;
import com.ISS.Booking_iss_tim21.repository.AccommodationPricingChangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationPricingChangeRequestService {
    @Autowired
    AccommodationPricingChangeRequestRepository repository;
    public List<AccommodationPricingChangeRequest> getAllForAccommodation(Long accommodationChangeRequestId){
        return repository.findAllForAccommodation(accommodationChangeRequestId);
    }
    public AccommodationPricingChangeRequest findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(AccommodationPricingChangeRequest accommodationPricingChangeRequest) { repository.save(accommodationPricingChangeRequest); }
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
