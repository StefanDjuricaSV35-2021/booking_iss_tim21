package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ReservationRepository reservationRepository;
    @MockBean
    private ReservationService reservationService;
    @Autowired
    EntityManager entityManager;


    @Test
    public void shouldSaveReservation() {
        Reservation reservation = new Reservation(15L, new User(), new Accommodation(), 1, 100, new TimeSlot(), ReservationStatus.Active);
        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation).usingRecursiveComparison().isEqualTo(reservation);
    }


    @Transactional
    @Test
    @Sql("classpath:test-data-res.sql")
    void testShouldDelete() {
        Reservation reservation = new Reservation(4L, new User(), new Accommodation(), 1, 100, new TimeSlot(), ReservationStatus.Active);

        reservationRepository.delete(reservation);

        Reservation deletedReservation = reservationRepository.findById(reservation.getId()).orElse(null);

        assertNull(deletedReservation);
    }

    @Test
    @Sql("classpath:test-data-res.sql")

    void testFindAll() {

        List<Reservation> reservations = reservationRepository.findAll();

        assertEquals(reservations.size(),5);
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testShouldFindById() {

        Reservation reservation = reservationRepository.findById(2L).orElse(null);
        assertEquals(reservation.getId(),2L);
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testShouldntFindById() {
        Reservation reservation = reservationRepository.findById(-1L).orElse(null);
        assertNull(reservation);
    }



    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetUsersReservationsById() {

        Long userId = 1L;
        List<Reservation> reservations = reservationRepository.getUsersReservationsById(userId);

        assertEquals(3,reservations.size());
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetAccommodationReservationsById() {

        Long accId = 1L;
        List<Reservation> reservations = reservationRepository.getReservationsByAccommodationId(accId);

        assertEquals(3,reservations.size());
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetUsersReservationByOwnerId() {

        Long ownerId = 2L;
        List<Reservation> reservations = reservationRepository.getOwnersReservationsById(ownerId);

        assertEquals(5,reservations.size());

    }


    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetAccommodationReservationByIdAndStatus() {

        Long accId = 1L;
        List<Reservation> reservations = reservationRepository.getReservationsByAccommodationIdAndStatus(accId, ReservationStatus.Active);

        assertEquals(1,reservations.size());
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetActiveUsersReservationsById() {

        Long userId = 1L;
        List<Reservation> reservations = reservationRepository.getActiveUsersReservationsById(userId);

        assertEquals(1,reservations.size());
    }
    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetActiveReservationsForAccommodation() {
        Long accId = 1L;
        List<Reservation> reservations = reservationRepository.getActiveReservationsForAccommodation(accId);

        assertEquals(1,reservations.size());
    }

    @Test
    @Sql("classpath:test-data-res.sql")
    void testGetFinishedReservationsForAccommodation() {
        Long accId = 1L;
        List<Reservation> reservations = reservationRepository.getFinishedReservationsForAccommodation(accId);

        assertEquals(1,reservations.size());
    }
}
