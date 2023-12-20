package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.UserActivationRequestDTO;
import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.UserActivationRequest;
import com.ISS.Booking_iss_tim21.repository.UserActivationRequestRepository;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserActivationRequestRepository userActivationRequestRepository;
    
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AccommodationService accommodationService;


    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<UserDTO>> getUsers() {

        List<User> users = userService.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) throws ConstraintViolationException {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userService.save(userDTO);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER', 'ROLE_ADMIN')")
    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) throws ConstraintViolationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userService.findById(userDTO.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!currentUsername.equals(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // if a user that has the same email exists, don't allow save
        User alreadyExists = userService.findByEmail(userDTO.getEmail());
        if (alreadyExists != null && alreadyExists.getId() != null && alreadyExists.getId() != userDTO.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!userDTO.getPassword().equals("")) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            userDTO.setPassword(user.getPassword());
        }

        user = userService.save(userDTO);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER')")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!currentUsername.equals(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (user.getRole() == Role.GUEST) {
            List<Reservation> currentActiveReservations = reservationService.getCurrentActiveReservationsById(user.getId());
            if(!currentActiveReservations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if (user.getRole() == Role.OWNER) {
            List<Accommodation> ownersAccommodations = accommodationService.getOwnersAccommodations(user.getId());
            for (Accommodation a : ownersAccommodations) {
                List<Reservation> reservations = reservationService.getCurrentActiveReservationsForAccommodation(a.getId());
                if (!reservations.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<UserDTO> getUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping("/activate/{email}")
    public ResponseEntity<UserActivationRequestDTO> isValidActivationRequest(@PathVariable String email){
        UserActivationRequest userActivationRequest = userActivationRequestRepository.findByEmail(email);

        if (userActivationRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userActivationRequest.isExpired()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new UserActivationRequestDTO(userActivationRequest.getEmail()) , HttpStatus.OK);
    }

    @GetMapping ("/activate/account/{email}")
    public ResponseEntity<UserDTO> activateAccount(@PathVariable String email){
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (user.isEnabled()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setEnabled(true);

        UserActivationRequest userActivationRequest = userActivationRequestRepository.findByEmail(email);
        userActivationRequestRepository.delete(userActivationRequest);

        user = userService.save(user);

        return new ResponseEntity<>(new UserDTO(user) , HttpStatus.OK);
    }
}

