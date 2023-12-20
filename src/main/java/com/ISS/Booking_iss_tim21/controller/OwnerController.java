package com.ISS.Booking_iss_tim21.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/owner")
public class OwnerController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Owner hello.");
    }
}
