package com.ISS.Booking_iss_tim21.model.review;


import com.ISS.Booking_iss_tim21.model.User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("OWNER")
public class OwnerReview extends Review {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User reviewed;



}

