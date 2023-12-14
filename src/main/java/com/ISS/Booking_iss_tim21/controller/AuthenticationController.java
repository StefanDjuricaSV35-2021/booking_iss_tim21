package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.RefreshTokenRequest;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import com.ISS.Booking_iss_tim21.dto.SignUpRequest;
import com.ISS.Booking_iss_tim21.exception.BadRequestException;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping(
            value = "/signOut",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity logoutUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            throw new BadRequestException("User is not authenticated!");
        }

    }


    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/confirm/{email}")
    public ResponseEntity<User> refresh(@PathVariable String email){
        return ResponseEntity.ok(authenticationService.confirmUser(email));
    }
}


