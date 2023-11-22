package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.ReservationDTO;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long accommodationId;
    private int guestsNumber;
    private double price;
    @Embedded
    private TimeSlot timeSlot;
    private ReservationStatus status;

    public Reservation(ReservationDTO reservationDTO) {
        this.id = reservationDTO.getId();
        this.userId = reservationDTO.getUserId();
        this.accommodationId = reservationDTO.getAccommodationId();
        this.guestsNumber = reservationDTO.getGuestsNumber();
        this.price = reservationDTO.getPrice();
        this.timeSlot = reservationDTO.getTimeSlot();
        this.status = reservationDTO.getStatus();
    }
}
