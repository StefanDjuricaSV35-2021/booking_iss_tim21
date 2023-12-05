package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.FavoriteAccommodation;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.repository.FavoriteAccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteAccommodationService {
    @Autowired
    FavoriteAccommodationRepository repository;


    public List<FavoriteAccommodation> findAll(){
        return repository.findAll();
    }

    public FavoriteAccommodation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public FavoriteAccommodation save(FavoriteAccommodation favoriteAccommodation) {
        return repository.save(favoriteAccommodation);
    }

    public List<FavoriteAccommodation> findUsersAccommodations(Long userId){
        List<FavoriteAccommodation> favoriteAccommodations = new ArrayList<>();
        for (FavoriteAccommodation favoriteAccommodation : findAll()){
            if(favoriteAccommodation.getUserId().equals(userId)){
                favoriteAccommodations.add(favoriteAccommodation);
            }
        }
        return favoriteAccommodations;
    }
}
