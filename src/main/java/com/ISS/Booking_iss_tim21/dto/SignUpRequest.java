package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;
    private Boolean isOwner;

}
