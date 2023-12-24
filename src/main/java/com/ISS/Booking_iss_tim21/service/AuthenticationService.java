package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.RefreshTokenRequest;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import com.ISS.Booking_iss_tim21.dto.SignUpRequest;
import com.ISS.Booking_iss_tim21.exception.BadRequestException;
import com.ISS.Booking_iss_tim21.exception.UserAlreadyExistsException;
import com.ISS.Booking_iss_tim21.exception.UserNotEnabledException;
import com.ISS.Booking_iss_tim21.model.EmailStructure;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.UserActivationRequest;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.UserActivationRequestRepository;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivationRequestRepository userActivationRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private EmailService emailService;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();


    public User signUp(SignUpRequest signUpRequest){

        User user = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);

        UserActivationRequest userActivationRequest = new UserActivationRequest();
        if(user != null){
            if (user.isEnabled()){
                throw new UserAlreadyExistsException("User with email: "+user.getEmail()+" already exists.");
            }
            userActivationRequest = userActivationRequestRepository.findByEmail(signUpRequest.getEmail());
        }else {
            user = new User();
            userActivationRequest.setEmail(signUpRequest.getEmail());
        }

        userActivationRequest.setDate(new Date());
        userActivationRequestRepository.save(userActivationRequest);

        Role role = signUpRequest.getIsOwner() ? Role.OWNER : Role.GUEST;
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setCountry(signUpRequest.getCountry());
        user.setCity(signUpRequest.getCity());
        user.setStreet(signUpRequest.getStreet());
        user.setPhone(signUpRequest.getPhone());

        user.setEnabled(false);
        user.setRole(role);

        emailService.sendEmail(user.getEmail(),new EmailStructure("Welcome to booʞing!!","Dear "+user.getName()+" "+user.getSurname()+
                ",\nYou have successfully signed up for booʞing services."+
                "\nAll that's left is to activate your account by clicking the following link in the next 24 hours: http://localhost:4200/activate/"+user.getEmail()+" ."+
                "\nWe are hoping to see you soon, the booʞing team."));

        userRepository.save(user);

        return user;
    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        securityContext.setAuthentication(auth);

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));


        if(!user.isEnabled()){
            throw new UserNotEnabledException("User account is not enabled.");
        }

        var jwt = jwtService.generateToken(user);

        user.setJwt(jwt);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        return jwtAuthenticationResponse;
    }

    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            return jwtAuthenticationResponse;
        }
        return null;
    }

    public User confirmUser(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email."));
        user.setEnabled(true);

        userRepository.save(user);

        return user;
    }

    public String logout() {
        Authentication auth =securityContext.getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return "You successfully logged out!";
        } else {
            throw new BadRequestException("User is not authenticated!");
        }
    }

    public Authentication getAuthentication(){
        return securityContext.getAuthentication();
    }
}


