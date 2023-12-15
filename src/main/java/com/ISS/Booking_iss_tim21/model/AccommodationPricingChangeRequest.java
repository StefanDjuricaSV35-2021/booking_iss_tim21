package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccommodationPricingChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_change_request_id")
    private AccommodationChangeRequest accommodationChangeRequest;
    private RequestStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    @Embedded
    private TimeSlot timeSlot;
    private double price;



}
