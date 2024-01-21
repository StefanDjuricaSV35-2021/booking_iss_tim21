package com.ISS.Booking_iss_tim21.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccommodationAnnualDataDTO {
    String name;
    double[] profit;
    int[] reservations;
}
