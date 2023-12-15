package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccommodationChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long requestCreationDate;
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    private Long ownerId;
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
    private Set<String> photos;
    private int daysForCancellation;


    @OneToMany(mappedBy = "accommodationChangeRequest", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationPricingChangeRequest> accommodationPricingChangeRequests;

}
