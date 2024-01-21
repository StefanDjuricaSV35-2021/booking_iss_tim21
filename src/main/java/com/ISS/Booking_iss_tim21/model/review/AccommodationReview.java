package com.ISS.Booking_iss_tim21.model.review;


import com.ISS.Booking_iss_tim21.model.Accommodation;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("ACCOMMODATION")
public class AccommodationReview extends Review {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation reviewed;



}

