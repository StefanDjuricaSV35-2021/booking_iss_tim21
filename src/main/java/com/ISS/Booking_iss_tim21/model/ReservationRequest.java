package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReservationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long accommodationId;
    private int guestsNumber;
    private double price;
    @Embedded
    private TimeSlot timeSlot;
    private ReservationRequestStatus status;

    public ReservationRequest(ReservationRequestDTO reservationRequestDTO) {
        this.id = reservationRequestDTO.getId();
        this.userId = reservationRequestDTO.getUserId();
        this.accommodationId = reservationRequestDTO.getAccommodationId();
        this.guestsNumber = reservationRequestDTO.getGuestsNumber();
        this.price = reservationRequestDTO.getPrice();
        this.timeSlot = reservationRequestDTO.getTimeSlot();
        this.status = reservationRequestDTO.getStatus();
    }
}
