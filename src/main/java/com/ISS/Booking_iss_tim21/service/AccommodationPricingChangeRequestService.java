package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingChangeRequestDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationPricingChangeRequest;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.repository.AccommodationPricingChangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationPricingChangeRequestService {
    @Autowired
    AccommodationPricingChangeRequestRepository repository;
    @Autowired
    AccommodationService accommodationService;
    @Autowired
    AccommodationPricingService accommodationPricingService;
    public List<AccommodationPricingChangeRequest> getAllForAccommodation(Long accommodationChangeRequestId){
        return repository.findAllForAccommodation(accommodationChangeRequestId);
    }
    public AccommodationPricingChangeRequest findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(AccommodationPricingChangeRequest accommodationPricingChangeRequest) { repository.save(accommodationPricingChangeRequest); }
    public void remove(Long id) {
        repository.deleteById(id);
    }

    public void checkOverlapAndAdjustTimeSlots(List<Reservation> reservations, AccommodationPricingChangeRequestDTO a) {
        Accommodation accommodation = accommodationService.findOne(a.getAccommodationId());

        if (reservations.isEmpty()) {
            accommodationPricingService.savePricingFromRequest(a, accommodation);
            return;
        }

        for (Reservation r : reservations) {
            if (r.getTimeSlot().getStartDate() > a.getTimeSlot().getStartDate() && r.getTimeSlot().getStartDate() < a.getTimeSlot().getEndDate() && r.getTimeSlot().getEndDate() >= a.getTimeSlot().getEndDate()) {
                a.getTimeSlot().setEndDate(r.getTimeSlot().getStartDate());
            } else if (r.getTimeSlot().getStartDate() <= a.getTimeSlot().getStartDate() && r.getTimeSlot().getEndDate() > a.getTimeSlot().getStartDate() && r.getTimeSlot().getStartDate() < a.getTimeSlot().getEndDate()) {
                a.getTimeSlot().setStartDate(r.getTimeSlot().getEndDate());
            } else if (r.getTimeSlot().getStartDate() <= a.getTimeSlot().getStartDate() && r.getTimeSlot().getEndDate() >= a.getTimeSlot().getEndDate()) {
                continue;
            } else if (r.getTimeSlot().getStartDate() > a.getTimeSlot().getEndDate() && r.getTimeSlot().getEndDate() < a.getTimeSlot().getEndDate()) {
                AccommodationPricingChangeRequestDTO pricingSecondPart = new AccommodationPricingChangeRequestDTO();
                pricingSecondPart.setId(0L);
                pricingSecondPart.setAccommodationId(a.getAccommodationId());
                pricingSecondPart.setPrice(a.getPrice());
                pricingSecondPart.setStatus(a.getStatus());
                pricingSecondPart.setAccommodationChangeRequestId(a.getAccommodationChangeRequestId());
                pricingSecondPart.setTimeSlot(new TimeSlot(r.getTimeSlot().getEndDate(), a.getTimeSlot().getEndDate()));

                checkOverlapAndAdjustTimeSlots(reservations, pricingSecondPart);
            }
        }
    }
}
