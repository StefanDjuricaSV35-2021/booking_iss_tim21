package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
class ReservationRequestRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Autowired
    EntityManager entityManager;

    public void rollbackDatabase(){
        InputStream inputStream=getClass().getClassLoader().getResourceAsStream("rollback.sql");
        String rollbackScript=new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
        entityManager.createNativeQuery(rollbackScript).executeUpdate();
    }


    @Test
    public void shouldSaveRequest() {
        ReservationRequest reservationRequest = new ReservationRequest(15L, new User(), new Accommodation(), 1, 100, new TimeSlot(), ReservationRequestStatus.Accepted);
        ReservationRequest savedReservationRequest = reservationRequestRepository.save(reservationRequest);

        assertThat(savedReservationRequest).usingRecursiveComparison().isEqualTo(reservationRequest);
    }


    @Transactional
    @Test
    @Sql("classpath:test-data-res-req.sql")
    void testShouldDelete() {
        // Save a user
        ReservationRequest reservationRequest = new ReservationRequest(1L, new User(), new Accommodation(), 1, 100, new TimeSlot(), ReservationRequestStatus.Accepted);

        reservationRequestRepository.delete(reservationRequest);

        ReservationRequest deletedReservationRequest = reservationRequestRepository.findById(reservationRequest.getId()).orElse(null);

        assertNull(deletedReservationRequest);
    }

    @Test
    @Sql("classpath:test-data-res-req.sql")
    void testFindAll() {

        List<ReservationRequest> allReservationRequests = reservationRequestRepository.findAll();

        assertEquals(allReservationRequests.size(),4);
    }

    @Test
    @Sql("classpath:test-data-res-req.sql")
    void testShouldFindById() {
        // Save a user

        ReservationRequest reservationRequest = reservationRequestRepository.findById(1L).orElse(null);
        assertEquals(reservationRequest.getId(),1L);
    }

    @Test
    @Sql("classpath:test-data-res-req.sql")
    void testShouldntFindById() {
        // Save a user
        ReservationRequest reservationRequest = reservationRequestRepository.findById(10L).orElse(null);
        assertNull(reservationRequest);
    }



    @Test
    @Sql("classpath:test-data-res-req.sql")
    void getUsersReservationRequestsById() {

        Long userId = 1L;
        List<ReservationRequest> reservationRequests = reservationRequestRepository.getUsersReservationRequestsById(userId);

        assertEquals(3,reservationRequests.size());
    }

    @Test
    @Sql("classpath:test-data-res-req.sql")
    void getAccommodationReservationRequestsById() {

        Long accId = 1L;
        List<ReservationRequest> reservationRequests = reservationRequestRepository.getAccommodationReservationRequestsById(accId);

        assertEquals(4,reservationRequests.size());
    }

    @Test
    @Sql("classpath:test-data-res-req.sql")
    void getUsersReservationRequestsByOwnerId() {

        Long ownerId = 2L;
        List<ReservationRequest> reservationRequests = reservationRequestRepository.getUsersReservationRequestsByOwnerId(ownerId);

        assertEquals(4,reservationRequests.size());

    }
}