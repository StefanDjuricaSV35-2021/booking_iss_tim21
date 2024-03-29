package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
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
    private boolean enabled;
    private boolean perNight;
    private boolean autoAccepting;

    @Transient
    private Double price=null;
  
    @ElementCollection(targetClass = Amenity.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<Amenity> amenities;

    @ElementCollection
    private List<String> photos;
    private int daysForCancellation;

    // @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade =
    // {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})

    @OneToMany(mappedBy = "reviewed", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationReview> accommodationReviews;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReservationRequest> reservationRequests;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationPricing> accommodationPricings;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationChangeRequest> accommodationChangeRequests;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationPricingChangeRequest> accommodationPricingChangeRequests;
}