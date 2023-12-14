package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long accommodationId;
    private int guestsNumber;
    private double price;
    private TimeSlot timeSlot;
    private ReservationStatus status;

    public ReservationDTO(Reservation reservation) {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<Reservation, ReservationDTO> propertyMap = new PropertyMap<Reservation, ReservationDTO>() {
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
        modelMapper.map(reservation, this);
    }
}
