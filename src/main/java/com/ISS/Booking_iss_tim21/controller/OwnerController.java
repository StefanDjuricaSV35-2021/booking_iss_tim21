package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.OwnerDTO;
import com.ISS.Booking_iss_tim21.model.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/owners")
public class OwnerController {

//    @Autowired
//    private OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getOwners() {

//        List<Owner> owners = ownerService.findAll();
        List<Owner> owners = new ArrayList<>();

        // convert courses to DTOs
        List<OwnerDTO> ownersDTO = new ArrayList<>();
        for (Owner s : owners) {
            ownersDTO.add(new OwnerDTO(s));
        }

        return new ResponseEntity<>(ownersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Integer id) {
//        Owner owner = ownerService.findOne(id);
        Owner owner = new Owner();

        // course must exist
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new OwnerDTO(owner), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OwnerDTO> saveOwner(@RequestBody OwnerDTO ownerDTO) {
        Owner owner = new Owner();

        owner.setEmail(ownerDTO.getEmail());
        owner.setPassword(ownerDTO.getPassword());
        owner.setName(ownerDTO.getName());
        owner.setSurname(ownerDTO.getSurname());
        owner.setCountry(ownerDTO.getCountry());
        owner.setCity(ownerDTO.getCity());
        owner.setStreet(ownerDTO.getStreet());
        owner.setPhone(ownerDTO.getPhone());

//        owner = ownerService.save(owner);
        return new ResponseEntity<>(new OwnerDTO(owner), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<OwnerDTO> updateOwner(@RequestBody OwnerDTO ownerDTO) {
        // a course must exist
//        Owner owner = ownerService.findOne(ownerDTO.getUserId());
        Owner owner = new Owner();

        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        owner.setEmail(ownerDTO.getEmail());
        owner.setPassword(ownerDTO.getPassword());
        owner.setName(ownerDTO.getName());
        owner.setSurname(ownerDTO.getSurname());
        owner.setCountry(ownerDTO.getCountry());
        owner.setCity(ownerDTO.getCity());
        owner.setStreet(ownerDTO.getStreet());
        owner.setPhone(ownerDTO.getPhone());

//        owner = ownerService.save(owner);
        return new ResponseEntity<>(new OwnerDTO(owner), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
//        Owner course = ownerService.findOne(id);
        Owner owner = new Owner();

        if (owner != null) {
//            ownerService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
