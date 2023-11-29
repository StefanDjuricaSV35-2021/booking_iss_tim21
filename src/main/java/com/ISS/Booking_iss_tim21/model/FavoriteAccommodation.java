package com.ISS.Booking_iss_tim21.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FavoriteAccommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteAccommodationId;

    private Long accommodationId;
    private Long userId;
}
