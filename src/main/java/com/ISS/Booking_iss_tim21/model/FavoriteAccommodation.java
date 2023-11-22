package com.ISS.Booking_iss_tim21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FavoriteAccommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteAccommodationId;

    private Long accommodationId;
    private Long userId;
}