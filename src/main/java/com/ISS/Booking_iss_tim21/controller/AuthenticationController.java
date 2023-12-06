package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.JwtAuthenticationRequest;
import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.dto.UserTokenState;
import com.ISS.Booking_iss_tim21.exception.ResourceConflictException;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.UserService;
import com.ISS.Booking_iss_tim21.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findOne(userDTO.getId());

        if (existUser != null) {
            throw new ResourceConflictException(userDTO.getId(), "Username already exists");
        }

        User user = this.userService.save(new User(userDTO));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}