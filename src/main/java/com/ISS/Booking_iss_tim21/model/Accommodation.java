package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    public Accommodation(AccommodationDetailsDTO accommodationDTO) {
        this.id = accommodationDTO.getId();
        this.ownerId = accommodationDTO.getOwnerId();
        this.name = accommodationDTO.getName();
        this.type = accommodationDTO.getType();
        this.minGuests = accommodationDTO.getMinGuests();
        this.maxGuests = accommodationDTO.getMaxGuests();
        this.description = accommodationDTO.getDescription();
        this.amenities = accommodationDTO.getAmenities();
        this.photos = accommodationDTO.getPhotos();
        this.daysForCancellation = accommodationDTO.getDaysForCancellation();
        this.location=accommodationDTO.getLocation();
    }
}
