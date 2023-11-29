package com.ISS.Booking_iss_tim21.controller;


import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.UserType;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {

        List<User> users = userService.getAll();

        // convert courses to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);


        // course must exist
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setType(userDTO.getType());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setStreet(userDTO.getStreet());
        user.setPhone(userDTO.getPhone());

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        User user = userService.findOne(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setId(userDTO.getId());
        user.setType(userDTO.getType());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setStreet(userDTO.getStreet());
        user.setPhone(userDTO.getPhone());

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        User user = userService.findOne(id);


        if (user != null) {
            userService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<List<UserDTO>> getTypeUsers(@PathVariable UserType type) {
        List<User> users = userService.getTypeUsers(type);


        // convert courses to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
}

