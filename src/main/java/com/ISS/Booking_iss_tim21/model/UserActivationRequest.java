package com.ISS.Booking_iss_tim21.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="`UserActivationRequests`")
public class UserActivationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private Date date;

    public boolean isExpired(){
        return (System.currentTimeMillis() - date.getTime()) >= (24 * 60 * 60 * 1000);
    }
}
