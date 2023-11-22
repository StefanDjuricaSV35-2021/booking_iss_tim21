package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccommodationPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accommodationId;
    @Embedded
    private TimeSlot timeSlot;
    private double price;

    public AccommodationPricing(AccommodationPricingDTO accommodationPricingDTO) {
        this.id = accommodationPricingDTO.getId();
        this.accommodationId = accommodationPricingDTO.getAccommodationId();
        this.timeSlot = accommodationPricingDTO.getTimeSlot();
        this.price = accommodationPricingDTO.getPrice();
    }
}
