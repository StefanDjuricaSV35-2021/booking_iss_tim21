package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
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
public class ReservationRequestDTO {
    private Long id;
    private Long userId;
    private Long accommodationId;
    private int guestsNumber;
    private double price;
    private TimeSlot timeSlot;
    private ReservationRequestStatus status;


    public ReservationRequestDTO(ReservationRequest reservationRequest) {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<ReservationRequest, ReservationRequestDTO> propertyMap = new PropertyMap<ReservationRequest, ReservationRequestDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setUserId(source.getUser().getId());
                map().setAccommodationId(source.getAccommodation().getId());
                map().setGuestsNumber(source.getGuestsNumber());
                map().setPrice(source.getPrice());
                map().setTimeSlot(source.getTimeSlot());
                map().setStatus(source.getStatus());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(reservationRequest, this);
    }

}
