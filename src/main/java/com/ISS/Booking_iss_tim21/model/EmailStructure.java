package com.ISS.Booking_iss_tim21.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailStructure {
    private String subject;
    private String message;
}
