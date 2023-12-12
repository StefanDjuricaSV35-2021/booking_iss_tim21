package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.FavoriteAccommodationDTO;
import com.ISS.Booking_iss_tim21.model.FavoriteAccommodation;
import com.ISS.Booking_iss_tim21.service.FavoriteAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth/favorite/accommodations")
public class FavoriteAccommodationController {

    @Autowired
    private FavoriteAccommodationService favoriteAccommodationService;

    @GetMapping
    public ResponseEntity<List<FavoriteAccommodationDTO>> getFavoriteAccommodations() {

        List<FavoriteAccommodation> favoriteAccommodations = favoriteAccommodationService.findAll();

        // convert courses to DTOs
        List<FavoriteAccommodationDTO> favoriteAccommodationsDTO = new ArrayList<>();
        for (FavoriteAccommodation s : favoriteAccommodations) {
            favoriteAccommodationsDTO.add(new FavoriteAccommodationDTO(s));
        }

        return new ResponseEntity<>(favoriteAccommodationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<FavoriteAccommodationDTO>> getUsersFavoriteAccommodations(@PathVariable Long id) {
        List<FavoriteAccommodation> favoriteAccommodations = favoriteAccommodationService.findUsersAccommodations(id);

        // convert courses to DTOs
        List<FavoriteAccommodationDTO> favoriteAccommodationsDTO = new ArrayList<>();
        for (FavoriteAccommodation s : favoriteAccommodations) {
            if (s.getUserId().equals(id)){
                favoriteAccommodationsDTO.add(new FavoriteAccommodationDTO(s));
            }
        }

        return new ResponseEntity<>(favoriteAccommodationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FavoriteAccommodationDTO> getFavoriteAccommodation(@PathVariable Long id) {
        FavoriteAccommodation favoriteAccommodation = favoriteAccommodationService.findOne(id);

        // course must exist
        if (favoriteAccommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new FavoriteAccommodationDTO(favoriteAccommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FavoriteAccommodationDTO> saveFavoriteAccommodation(@RequestBody FavoriteAccommodationDTO favoriteAccommodationDTO) {
        FavoriteAccommodation favoriteAccommodation = new FavoriteAccommodation();

        favoriteAccommodation.setFavoriteAccommodationId(favoriteAccommodationDTO.getFavoriteAccommodationId());
        favoriteAccommodation.setAccommodationId(favoriteAccommodationDTO.getAccommodationId());
        favoriteAccommodation.setUserId(favoriteAccommodationDTO.getUserId());

        favoriteAccommodation = favoriteAccommodationService.save(favoriteAccommodation);
        return new ResponseEntity<>(new FavoriteAccommodationDTO(favoriteAccommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<FavoriteAccommodationDTO> updateFavoriteAccommodation(@RequestBody FavoriteAccommodationDTO favoriteAccommodationDTO) {
        // a course must exist
        FavoriteAccommodation favoriteAccommodation = favoriteAccommodationService.findOne(favoriteAccommodationDTO.getUserId());

        if (favoriteAccommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        favoriteAccommodation.setFavoriteAccommodationId(favoriteAccommodationDTO.getFavoriteAccommodationId());
        favoriteAccommodation.setAccommodationId(favoriteAccommodationDTO.getAccommodationId());
        favoriteAccommodation.setUserId(favoriteAccommodationDTO.getUserId());

        favoriteAccommodation = favoriteAccommodationService.save(favoriteAccommodation);
        return new ResponseEntity<>(new FavoriteAccommodationDTO(favoriteAccommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFavoriteAccommodation(@PathVariable Long id) {
        FavoriteAccommodation favoriteAccommodation = favoriteAccommodationService.findOne(id);

        if (favoriteAccommodation != null) {
            favoriteAccommodationService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
