package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.user.Id = ?1")
    public List<Reservation> getUsersReservationsById(Long userId);

    @Query("select r from Reservation r where r.accommodation.owner.Id = ?1")
    public List<Reservation> getOwnersReservationsById(Long ownerId);

    @Query("select r from Reservation r where r.accommodation.id = ?1")
    public List<Reservation> getReservationsByAccommodationId(Long accommodation_id);

    @Query("select r from Reservation r where r.accommodation.id = ?1 and r.status=?2")
    public List<Reservation> getReservationsByAccommodationIdAndStatus(Long accommodation_id, ReservationStatus status);


    @Query("select r from Reservation r where r.user.Id = ?1 and r.status = com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus.Active")
    public List<Reservation> getActiveUsersReservationsById(Long userId);
    @Query("select r from Reservation r where r.accommodation.id = ?1 and r.status = com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus.Active")

    public List<Reservation> getActiveReservationsForAccommodation(Long accommodationId);

    @Query("select r from Reservation r where r.accommodation.id = ?1 and r.status = com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus.Finished")

    public List<Reservation> getFinishedReservationsForAccommodation(Long accommodationId);
}
