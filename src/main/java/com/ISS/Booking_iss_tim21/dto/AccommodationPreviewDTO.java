package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
@AllArgsConstructor
public class AccommodationPreviewDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private AccommodationType type;

    public AccommodationPreviewDTO(Accommodation accommodation) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(accommodation, this);
    }
}
