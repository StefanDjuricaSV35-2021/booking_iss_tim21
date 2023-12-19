package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationPricingDTO {
    private Long id;
    private Long accommodationId;
    private TimeSlot timeSlot;
    private double price;

    public AccommodationPricingDTO(AccommodationPricing accommodationPricing) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<AccommodationPricing, AccommodationPricingDTO> propertyMap = new PropertyMap<AccommodationPricing, AccommodationPricingDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setAccommodationId(source.getAccommodation().getId());
                map().setTimeSlot(source.getTimeSlot());
                map().setPrice(source.getPrice());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(accommodationPricing, this);
    }
}
