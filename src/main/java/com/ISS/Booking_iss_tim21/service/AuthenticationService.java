package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.SignUpRequest;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest signUpRequest){
        User user = new User();
        Role role = signUpRequest.getIsOwner() ? Role.OWNER : Role.GUEST;
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setCountry(signUpRequest.getCountry());
        user.setCity(signUpRequest.getCity());
        user.setStreet(signUpRequest.getStreet());
        user.setPhone(signUpRequest.getPhone());
        user.setEnabled(true);
//        treba false pa da se pretvori u true kada potvrdi email
//        user.setEnabled(false);
        user.setRole(role);

        userRepository.save(user);

        return user;
    }
}
