package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.repository.AccommodationPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccommodationPricingService {
    @Autowired
    AccommodationPricingRepository repository;

    public List<AccommodationPricing> getAll(){
        return repository.findAll();
    }


    public AccommodationPricing findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(AccommodationPricing accommodationPricing) { repository.save(accommodationPricing); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<AccommodationPricing> getAccommodationPricingsForAccommodation(Long accommodationId) { return repository.getAccommodationPricingsForAccommodation(accommodationId); }

    public List<AccommodationPricing> getActiveAccommodationPricings(Long accommodationId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<AccommodationPricing> allPricings = repository.getAccommodationPricingsForAccommodation(accommodationId);
        List<AccommodationPricing> currentPricings = new ArrayList<>();
        for (AccommodationPricing a : allPricings) {
            if (a.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentPricings.add(a);
        }
        return currentPricings;
    }
}
