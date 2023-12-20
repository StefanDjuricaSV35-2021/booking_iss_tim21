package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import com.ISS.Booking_iss_tim21.model.AccommodationPricingChangeRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationPricingChangeRequestDTO {

    private Long id;
    private Long accommodationChangeRequestId;
    private RequestStatus status;
    private Long accommodationId;
    private TimeSlot timeSlot;
    private double price;


    public AccommodationPricingChangeRequestDTO(AccommodationPricingChangeRequest pricingChangeRequest) {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<AccommodationPricingChangeRequest, AccommodationPricingChangeRequestDTO> propertyMap =
                new PropertyMap<AccommodationPricingChangeRequest, AccommodationPricingChangeRequestDTO>() {
                    protected void configure() {
                        map().setId(source.getId());
                        map().setAccommodationChangeRequestId(source.getAccommodationChangeRequest().getId());
                        map().setStatus(source.getStatus());
                        map().setAccommodationId(source.getAccommodation().getId());
                        map().setTimeSlot(source.getTimeSlot());
                        map().setPrice(source.getPrice());
                    }
                };

        modelMapper.addMappings(propertyMap);

        modelMapper.map(pricingChangeRequest, this);
    }
}
