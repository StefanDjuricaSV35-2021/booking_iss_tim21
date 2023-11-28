package com.ISS.Booking_iss_tim21.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TimeSlot {
    private long startDate;
    private long endDate;
}
