package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccommodationService {
    @Autowired
    AccommodationRepository repository;

    public List<Accommodation> getAll(){
        return repository.findAll();
    }


    public Accommodation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(Accommodation accommodation) { repository.save(accommodation); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<Accommodation> getOwnersAccommodations(Long ownerId) { return repository.getOwnersAccommodations(ownerId); }

}
