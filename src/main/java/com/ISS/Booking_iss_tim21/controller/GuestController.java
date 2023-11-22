package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.GuestDTO;
import com.ISS.Booking_iss_tim21.model.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping(value = "api/guests")
public class GuestController {

//    @Autowired
//    private GuestService guestService;

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getGuests() {

//        List<Guest> guests = guestService.findAll();
        List<Guest> guests = new ArrayList<>();

        // convert courses to DTOs
        List<GuestDTO> guestsDTO = new ArrayList<>();
        for (Guest s : guests) {
            guestsDTO.add(new GuestDTO(s));
        }

        return new ResponseEntity<>(guestsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable Integer id) {
//        Guest guest = guestService.findOne(id);
        Guest guest = new Guest();

        // course must exist
        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDTO> saveGuest(@RequestBody GuestDTO guestDTO) {
        Guest guest = new Guest();

        guest.setEmail(guestDTO.getEmail());
        guest.setPassword(guestDTO.getPassword());
        guest.setName(guestDTO.getName());
        guest.setSurname(guestDTO.getSurname());
        guest.setCountry(guestDTO.getCountry());
        guest.setCity(guestDTO.getCity());
        guest.setStreet(guestDTO.getStreet());
        guest.setPhone(guestDTO.getPhone());

//        guest = guestService.save(guest);
        return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        // a course must exist
//        Guest guest = guestService.findOne(guestDTO.getUserId());
        Guest guest = new Guest();

        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        guest.setEmail(guestDTO.getEmail());
        guest.setPassword(guestDTO.getPassword());
        guest.setName(guestDTO.getName());
        guest.setSurname(guestDTO.getSurname());
        guest.setCountry(guestDTO.getCountry());
        guest.setCity(guestDTO.getCity());
        guest.setStreet(guestDTO.getStreet());
        guest.setPhone(guestDTO.getPhone());

//        guest = guestService.save(guest);
        return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Integer id) {
//        Guest course = guestService.findOne(id);
        Guest guest = new Guest();

        if (guest != null) {
//            guestService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

