package com.ISS.Booking_iss_tim21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AccommodationPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accommodationId;
    @Embedded
    private TimeSlot timeSlot;
    private double price;
}
