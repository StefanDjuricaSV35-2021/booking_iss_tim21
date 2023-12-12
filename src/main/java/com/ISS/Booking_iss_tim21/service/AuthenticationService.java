package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.RefreshTokenRequest;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import com.ISS.Booking_iss_tim21.dto.SignUpRequest;
import com.ISS.Booking_iss_tim21.exception.UserAlreadyExistsException;
import com.ISS.Booking_iss_tim21.exception.UserNotEnabledException;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest){

        User user = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);

        if(user != null){
            if (user.isEnabled()){
                throw new UserAlreadyExistsException("User with email: "+user.getEmail()+" already exists.");
            }else {
                user.setEnabled(true);
            }
        }else {
            user = new User();
        }


        Role role = signUpRequest.getIsOwner() ? Role.OWNER : Role.GUEST;
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setCountry(signUpRequest.getCountry());
        user.setCity(signUpRequest.getCity());
        user.setStreet(signUpRequest.getStreet());
        user.setPhone(signUpRequest.getPhone());
        if(user.isEnabled()){
            //send new email
        }
        user.setEnabled(true);
//        treba false pa da se pretvori u true kada potvrdi email
//        user.setEnabled(false);
        user.setRole(role);

        userRepository.save(user);

        return user;
    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if(!user.isEnabled()){
            throw new UserNotEnabledException("User account is not enabled.");
        }
        var jwt = jwtService.gemerateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.gemerateToken(user);

            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}


