package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    private String name;
    private AccommodationType type;
    private int minGuests;
    private int maxGuests;
    private String description;
    private String location;

    @ElementCollection(targetClass = Amenity.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Amenity> amenities;

    @ElementCollection
    private Set<String> photos;    priate int daysForCancellation;
    private boolean perNight;
    private boolean enabled;

    //@OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})

    @OnToMany(mappedBy = "reviewed", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationReview> accommodationReviews;
 
    // 

    
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReservationRequest> reservationRequests;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationChangeRequest> accommodationChangeRequests;

    @OneToMany(mappedBy = "accommodation", fetch = FetchT

     


    