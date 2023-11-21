package com.ISS.Booking_iss_tim21.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@Entity
public class TimeSlot {
    Date startDate;
    Date endDate;
}
