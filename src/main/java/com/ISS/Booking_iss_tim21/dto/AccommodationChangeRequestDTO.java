package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationChangeRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long requestCreationDate;
    private RequestStatus status;

    private Long accommodationId;
    private Long ownerId;
    private String name;
    private AccommodationType type;
    private int minGuests;
    private int maxGuests;
    private String description;
    private String location;
    private Set<Amenity> amenities;
    private Set<String> photos;
    private int daysForCancellation;
    private boolean perNight;
    private boolean enabled;

    public AccommodationChangeRequestDTO(AccommodationChangeRequest accommodationChangeRequest) {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<AccommodationChangeRequest, AccommodationChangeRequestDTO> propertyMap =
                new PropertyMap<AccommodationChangeRequest, AccommodationChangeRequestDTO>() {
                    protected void configure() {
                        map().setId(source.getId());
                        map().setRequestCreationDate(source.getRequestCreationDate());
                        map().setStatus(source.getStatus());
                        map().setAccommodationId(source.getAccommodation().getId());
                        map().setOwnerId(source.getOwnerId());
                        map().setName(source.getName());
                        map().setType(source.getType());
                        map().setMinGuests(source.getMinGuests());
                        map().setMaxGuests(source.getMaxGuests());
                        map().setDescription(source.getDescription());
                        map().setLocation(source.getLocation());
                        map().setAmenities(source.getAmenities());
                        map().setPhotos(source.getPhotos());
                        map().setDaysForCancellation(source.getDaysForCancellation());
                        map().setPerNight(source.isPerNight());
                        map().setEnabled(source.isEnabled());
                    }
                };

        modelMapper.addMappings(propertyMap);

        modelMapper.map(accommodationChangeRequest, this);
    }
}
