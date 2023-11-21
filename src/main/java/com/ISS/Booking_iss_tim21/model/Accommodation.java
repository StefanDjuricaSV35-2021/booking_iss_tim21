package com.ISS.Booking_iss_tim21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String name;
    private AccommodationType type;
    private int minGuests;
    private int maxGuests;
    private String description;
    private HashSet<Amenity> amenities;
    private HashSet<String> photos;
    private int daysForCancellation;
}
