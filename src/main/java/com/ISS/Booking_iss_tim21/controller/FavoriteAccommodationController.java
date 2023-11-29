package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.FavoriteAccommodationDTO;
import com.ISS.Booking_iss_tim21.model.FavoriteAccommodation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "favorite-accommodations")
public class FavoriteAccommodationController {

//    @Autowired
//    private FavoriteAccommodationService favoriteAccommodationService;

    private static List<FavoriteAccommodation> mockedFavoriteAccommodations = new ArrayList<>();

    static {
        mockedFavoriteAccommodations.add(new FavoriteAccommodation(1L, 1L, 101L));
        mockedFavoriteAccommodations.add(new FavoriteAccommodation(2L, 2L, 102L));
        mockedFavoriteAccommodations.add(new FavoriteAccommodation(3L, 3L, 103L));
    }
    @GetMapping
    public ResponseEntity<List<FavoriteAccommodationDTO>> getFavoriteAccommodations() {

//        List<FavoriteAccommodation> favoriteAccommodations = favoriteAccommodationService.findAll();
        List<FavoriteAccommodation> favoriteAccommodations = mockedFavoriteAccommodations;

        // convert courses to DTOs
        List<FavoriteAccommodationDTO> favoriteAccommodationsDTO = new ArrayList<>();
        for (FavoriteAccommodation s : favoriteAccommodations) {
            favoriteAccommodationsDTO.add(new FavoriteAccommodationDTO(s));
        }

        return new ResponseEntity<>(favoriteAccommodationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<FavoriteAccommodationDTO>> getUsersFavoriteAccommodations(@PathVariable Long id) {
//        List<FavoriteAccommodation> favoriteAccommodations = favoriteAccommodationService.findUsersAccommodations(id);
        List<FavoriteAccommodation> favoriteAccommodations = mockedFavoriteAccommodations;

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
//        FavoriteAccommodation favoriteAccommodation = favoriteAccommodationService.findOne(id);
        FavoriteAccommodation favoriteAccommodation = findOne(id);

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

//        favoriteAccommodation = favoriteAccommodationService.save(favoriteAccommodation);
        favoriteAccommodation = save(favoriteAccommodation);
        return new ResponseEntity<>(new FavoriteAccommodationDTO(favoriteAccommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<FavoriteAccommodationDTO> updateFavoriteAccommodation(@RequestBody FavoriteAccommodationDTO favoriteAccommodationDTO) {
        // a course must exist
//        FavoriteAccommodation favoriteAccommodation = favoriteAccommodationService.findOne(favoriteAccommodationDTO.getUserId());
        FavoriteAccommodation favoriteAccommodation = findOne(favoriteAccommodationDTO.getFavoriteAccommodationId());

        if (favoriteAccommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        favoriteAccommodation.setFavoriteAccommodationId(favoriteAccommodationDTO.getFavoriteAccommodationId());
        favoriteAccommodation.setAccommodationId(favoriteAccommodationDTO.getAccommodationId());
        favoriteAccommodation.setUserId(favoriteAccommodationDTO.getUserId());

//        favoriteAccommodation = favoriteAccommodationService.save(favoriteAccommodation);
        favoriteAccommodation = save(favoriteAccommodation);
        return new ResponseEntity<>(new FavoriteAccommodationDTO(favoriteAccommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFavoriteAccommodation(@PathVariable Long id) {
//        FavoriteAccommodation course = favoriteAccommodationService.findOne(id);
        FavoriteAccommodation favoriteAccommodation = findOne(id);

        if (favoriteAccommodation != null) {
//            favoriteAccommodationService.remove(id);
            mockedFavoriteAccommodations.remove(favoriteAccommodation);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private FavoriteAccommodation findOne(Long id){
        for (FavoriteAccommodation f: mockedFavoriteAccommodations){
            if (f.getFavoriteAccommodationId().equals(id)){
                return f;
            }
        }
        return null;
    }

    private FavoriteAccommodation save( FavoriteAccommodation favoriteAccommodation){
        mockedFavoriteAccommodations.add(favoriteAccommodation);
        return findOne(favoriteAccommodation.getFavoriteAccommodationId());
    }
}
