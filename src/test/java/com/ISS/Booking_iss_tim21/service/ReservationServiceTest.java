package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import com.ISS.Booking_iss_tim21.repository.ReservationRequestRepository;
import com.beust.ah.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;
    @Mock
    ReservationRequestService reservationRequestService;

    @Captor
    private ArgumentCaptor<Reservation> reservationArgumentCaptor;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testAcceptReservationNoOverlap() {
        ReservationRequest mainRequest = setUpMainRequest();
        List<ReservationRequest> reservationRequestsBefore = setUpNoOverlapRequests();
        List<ReservationRequest> reservationRequests = setUpNoOverlapRequests();
        Mockito.when(reservationRequestService.getAccommodationReservationRequestsById(any())).thenReturn(reservationRequests);

        reservationService.acceptReservation(mainRequest);
        Assertions.assertEquals(numberOfStatusesChanged(reservationRequestsBefore, reservationRequests), 0);

        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        Assertions.assertEquals(reservationArgumentCaptor.getValue().getStatus(), ReservationStatus.Active);

    }

    @Test
    public void testAcceptReservationOverLapStartDate() {
        ReservationRequest mainRequest = setUpMainRequest();
        List<ReservationRequest> reservationRequestsBefore = setUpOverlapStartDateRequests();
        List<ReservationRequest> reservationRequests = setUpOverlapStartDateRequests();
        Mockito.when(reservationRequestService.getAccommodationReservationRequestsById(any())).thenReturn(reservationRequests);

        reservationService.acceptReservation(mainRequest);

        Assertions.assertEquals(numberOfStatusesChanged(reservationRequestsBefore, reservationRequests), 1);
        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        Assertions.assertEquals(reservationArgumentCaptor.getValue().getStatus(), ReservationStatus.Active);
    }

    @Test
    public void testAcceptReservationOverLapEndDate() {
        ReservationRequest mainRequest = setUpMainRequest();
        List<ReservationRequest> reservationRequestsBefore = setUpOverlapEndDateRequests();
        List<ReservationRequest> reservationRequests = setUpOverlapEndDateRequests();
        Mockito.when(reservationRequestService.getAccommodationReservationRequestsById(any())).thenReturn(reservationRequests);

        reservationService.acceptReservation(mainRequest);

        Assertions.assertEquals(numberOfStatusesChanged(reservationRequestsBefore, reservationRequests), 1);
        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        Assertions.assertEquals(reservationArgumentCaptor.getValue().getStatus(), ReservationStatus.Active);
    }

    @Test
    public void testAcceptReservationOverLapWholeRequest() {
        ReservationRequest mainRequest = setUpMainRequest();
        List<ReservationRequest> reservationRequestsBefore = setUpOverlapWholeRequest();
        List<ReservationRequest> reservationRequests = setUpOverlapWholeRequest();
        Mockito.when(reservationRequestService.getAccommodationReservationRequestsById(any())).thenReturn(reservationRequests);

        reservationService.acceptReservation(mainRequest);

        Assertions.assertEquals(numberOfStatusesChanged(reservationRequestsBefore, reservationRequests), 1);
        verify(reservationRepository, times(1)).save(reservationArgumentCaptor.capture());
        Assertions.assertEquals(reservationArgumentCaptor.getValue().getStatus(), ReservationStatus.Active);
    }

    public int numberOfStatusesChanged(List<ReservationRequest> reservationRequestsBefore, List<ReservationRequest> reservationRequests) {
        int counter = 0;
        for (int i = 0; i < reservationRequestsBefore.size(); i++) {
            if(reservationRequestsBefore.get(i).getStatus() != reservationRequests.get(i).getStatus()) counter++;
        }
        return counter;
    }


    private ReservationRequest setUpMainRequest() {
        ReservationRequest mainRequest = new ReservationRequest();
        mainRequest.setId(3L);
        mainRequest.setStatus(ReservationRequestStatus.Accepted);
        mainRequest.setPrice(200.0);
        mainRequest.setTimeSlot(new TimeSlot(1000, 5000));
        mainRequest.setUser(new User());
        mainRequest.setAccommodation(new Accommodation());
        mainRequest.setGuestsNumber(3);
        return mainRequest;
    }

    private List<ReservationRequest> setUpNoOverlapRequests() {
        List<ReservationRequest> reservationRequests = new ArrayList<>();
        ReservationRequest res1 = new ReservationRequest();
        res1.setId(1L);
        res1.setStatus(ReservationRequestStatus.Waiting);
        res1.setPrice(200.0);
        res1.setTimeSlot(new TimeSlot(6000, 7000));
        res1.setUser(new User());
        res1.setAccommodation(new Accommodation());
        res1.setGuestsNumber(3);
        ReservationRequest res2 = new ReservationRequest();
        res2.setId(1L);
        res2.setStatus(ReservationRequestStatus.Waiting);
        res2.setPrice(200.0);
        res2.setTimeSlot(new TimeSlot(8000, 10000));
        res2.setUser(new User());
        res2.setAccommodation(new Accommodation());
        res2.setGuestsNumber(3);

        reservationRequests.add(res2);
        reservationRequests.add(res1);
        return reservationRequests;
    }

    private List<ReservationRequest> setUpOverlapStartDateRequests() {
        List<ReservationRequest> reservationRequests = new ArrayList<>();
        ReservationRequest res1 = new ReservationRequest();
        res1.setId(1L);
        res1.setStatus(ReservationRequestStatus.Waiting);
        res1.setPrice(200.0);
        res1.setTimeSlot(new TimeSlot(3000, 7000));
        res1.setUser(new User());
        res1.setAccommodation(new Accommodation());
        res1.setGuestsNumber(3);
        ReservationRequest res2 = new ReservationRequest();
        res2.setId(1L);
        res2.setStatus(ReservationRequestStatus.Waiting);
        res2.setPrice(200.0);
        res2.setTimeSlot(new TimeSlot(8000, 10000));
        res2.setUser(new User());
        res2.setAccommodation(new Accommodation());
        res2.setGuestsNumber(3);

        reservationRequests.add(res2);
        reservationRequests.add(res1);
        return reservationRequests;
    }
    private List<ReservationRequest> setUpOverlapEndDateRequests() {
        List<ReservationRequest> reservationRequests = new ArrayList<>();
        ReservationRequest res1 = new ReservationRequest();
        res1.setId(1L);
        res1.setStatus(ReservationRequestStatus.Waiting);
        res1.setPrice(200.0);
        res1.setTimeSlot(new TimeSlot(500, 3000));
        res1.setUser(new User());
        res1.setAccommodation(new Accommodation());
        res1.setGuestsNumber(3);
        ReservationRequest res2 = new ReservationRequest();
        res2.setId(1L);
        res2.setStatus(ReservationRequestStatus.Waiting);
        res2.setPrice(200.0);
        res2.setTimeSlot(new TimeSlot(8000, 10000));
        res2.setUser(new User());
        res2.setAccommodation(new Accommodation());
        res2.setGuestsNumber(3);

        reservationRequests.add(res2);
        reservationRequests.add(res1);
        return reservationRequests;
    }
    private List<ReservationRequest> setUpOverlapWholeRequest() {
        List<ReservationRequest> reservationRequests = new ArrayList<>();
        ReservationRequest res1 = new ReservationRequest();
        res1.setId(1L);
        res1.setStatus(ReservationRequestStatus.Waiting);
        res1.setPrice(200.0);
        res1.setTimeSlot(new TimeSlot(500, 7000));
        res1.setUser(new User());
        res1.setAccommodation(new Accommodation());
        res1.setGuestsNumber(3);
        ReservationRequest res2 = new ReservationRequest();
        res2.setId(1L);
        res2.setStatus(ReservationRequestStatus.Waiting);
        res2.setPrice(200.0);
        res2.setTimeSlot(new TimeSlot(8000, 10000));
        res2.setUser(new User());
        res2.setAccommodation(new Accommodation());
        res2.setGuestsNumber(3);

        reservationRequests.add(res2);
        reservationRequests.add(res1);
        return reservationRequests;
    }
}
