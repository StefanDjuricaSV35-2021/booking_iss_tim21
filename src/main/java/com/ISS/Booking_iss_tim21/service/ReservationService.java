package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.dateStringToUnix;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    @Autowired
    ReservationRequestService reservationRequestService;

    public List<Reservation> getAll(){
        return repository.findAll();
    }

    public List<Reservation> getAccommodationReservations(Long id){return repository.getReservationsByAccommodationId(id);}

    public List<Reservation> getActiveAccommodationReservations(Long id){return repository.getReservationsByAccommodationIdAndStatus(id, ReservationStatus.Active);}

    public Reservation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(Reservation reservation) { repository.save(reservation); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<Reservation> getUsersReservationsById(Long userId) { return repository.getUsersReservationsById(userId); }

    public List<Reservation> getActiveUsersReservationsById(Long userId) { return repository.getActiveUsersReservationsById(userId); }

    public List<Reservation> getOwnerReservations(Long ownerId){
        return repository.getOwnersReservationsById(ownerId);
    }

    public void acceptReservation(ReservationRequest request){
        checkOverlapAndUpdateRequests(request);

        Reservation reservation= new Reservation();
        reservation.setStatus(ReservationStatus.Active);
        reservation.setUser(request.getUser());
        reservation.setPrice(request.getPrice());
        reservation.setAccommodation(request.getAccommodation());
        reservation.setTimeSlot(request.getTimeSlot());
        reservation.setGuestsNumber(request.getGuestsNumber());
        save(reservation);
    }

    private void checkOverlapAndUpdateRequests(ReservationRequest request) {
        List<ReservationRequest> requests = reservationRequestService.getAccommodationReservationRequestsById(request.getAccommodation().getId());
        for (ReservationRequest r : requests) {
            if (r.getStatus() != ReservationRequestStatus.Waiting) continue;
            if (r.getTimeSlot().getStartDate() >= request.getTimeSlot().getStartDate() && r.getTimeSlot().getStartDate() < request.getTimeSlot().getEndDate()) {
                r.setStatus(ReservationRequestStatus.Declined);
                reservationRequestService.save(r);
            } else if (r.getTimeSlot().getEndDate() <= request.getTimeSlot().getEndDate() && r.getTimeSlot().getEndDate() > request.getTimeSlot().getStartDate()) {
                r.setStatus(ReservationRequestStatus.Declined);
                reservationRequestService.save(r);
            } else if (r.getTimeSlot().getStartDate() <= request.getTimeSlot().getStartDate() && r.getTimeSlot().getEndDate() >= request.getTimeSlot().getEndDate()) {
                r.setStatus(ReservationRequestStatus.Declined);
                reservationRequestService.save(r);
            }
        }
    }

    public List<Reservation> getOwnerReservationsBetweenDates(Long ownerId,String dateFrom,String dateTo){
        Long unixDateFrom=dateStringToUnix(dateFrom);
        Long unixDateTo=dateStringToUnix(dateTo);

        List<Reservation> validReservations= new ArrayList<>();
        List<Reservation> allReservations= repository.getOwnersReservationsById(ownerId);

        for (Reservation r : allReservations){
            TimeSlot ts=r.getTimeSlot();

            if(max(ts.getStartDate(), unixDateFrom) < min(ts.getEndDate(), unixDateTo)){
                validReservations.add(r);
            }
        }

        return validReservations;

    }

    public List<Reservation> getAccommodationReservationsBetweenDates(Long accId,String dateFrom,String dateTo){
        Long unixDateFrom=dateStringToUnix(dateFrom);
        Long unixDateTo=dateStringToUnix(dateTo);

        List<Reservation> validReservations= new ArrayList<>();
        List<Reservation> allReservations= repository.getReservationsByAccommodationId(accId);

        for (Reservation r : allReservations){
            TimeSlot ts=r.getTimeSlot();

            if(max(ts.getStartDate(), unixDateFrom) < min(ts.getEndDate(), unixDateTo)){
                validReservations.add(r);
            }
        }

        return validReservations;

    }



    public List<Reservation> getCurrentActiveReservationsForAccommodation(Long accommodationId) {
        long currentUnixTimestamp = System.currentTimeMillis() / AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.getActiveReservationsForAccommodation(accommodationId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }


    public List<Reservation> getCurrentActiveReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.getActiveUsersReservationsById(userId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }
    public List<Reservation> getCurrentReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }

    public List<Reservation> getCurrentOwnersReservationsById(Long ownerId) {
        long currentUnixTimestamp = System.currentTimeMillis() / AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.getOwnersReservationsById(ownerId);
        List<Reservation> currentReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentReservations.add(r);
        }
        return currentReservations;
    }

    public List<Reservation> getFinishedUsersReservationsForOwner(Long userId, Long ownerId){
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getStatus().equals(ReservationStatus.Finished) && r.getAccommodation().getOwner().getId().equals(ownerId))
                reservations.add(r);
        }
        return reservations;
    }

    public void updateReservations(){
        long currentUnixTimestamp = System.currentTimeMillis()/AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.findAll();
        for (Reservation r : allReservations) {
            if(r.getTimeSlot().getEndDate() <= currentUnixTimestamp && r.getStatus().equals(ReservationStatus.Active)){
                r.setStatus(ReservationStatus.Finished);
                repository.save(r);
            }
        }
    }

    public boolean isValidForReview(Long userId, Long accommodationId){
        long currentUnixTimestamp = System.currentTimeMillis()/AppConfig.UNIX_DIFF;
        List<Reservation> allReservations = repository.getFinishedReservationsForAccommodation(accommodationId);
        for (Reservation r : allReservations) {
            if (r.getUser().getId().equals(userId) && r.getTimeSlot().getEndDate()+ (7 * 24 * 60 * 60 * 1000) >= currentUnixTimestamp){//(7 * 24 * 60 * 60 * 1000) je sedam dana
                return true;
            }
        }

        return false;
    }
}
