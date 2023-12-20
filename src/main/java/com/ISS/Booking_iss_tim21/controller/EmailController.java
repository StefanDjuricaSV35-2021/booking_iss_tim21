package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.SignUpRequest;
import com.ISS.Booking_iss_tim21.model.EmailStructure;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send/{email}")
    public String sendMail(@PathVariable String email, @RequestBody EmailStructure emailStructure){
        emailService.sendEmail(email,emailStructure);
        return "Email sent!!";
    }

}
