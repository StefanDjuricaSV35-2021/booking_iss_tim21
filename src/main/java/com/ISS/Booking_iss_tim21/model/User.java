package com.ISS.Booking_iss_tim21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;

    @Embedded
    private ArrayList<Accommodation> favoriteApartments;
}
