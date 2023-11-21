package com.ISS.Booking_iss_tim21.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long accommodationId;
    private int guestsNumber;
    private double price;
    @Embedded
    private TimeSlot timeSlot;
    private ReservationRequest status;
}
