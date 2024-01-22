package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.exception.DatesOverlapException;
import com.ISS.Booking_iss_tim21.exception.InvalidIdException;
import com.ISS.Booking_iss_tim21.exception.NullIdException;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import com.ISS.Booking_iss_tim21.repository.ReservationRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReservationRequestServiceTest {

    @Mock
    ReservationRequestRepository reservationRequestRepository;
    @Mock
    ReservationService reservationService;
    @Mock
    AccommodationService accommodationService;
    @Mock
    UserService userService;
    @Captor
    private ArgumentCaptor<ReservationRequest> reservationRequestArgumentCaptor;
    @InjectMocks
    private ReservationRequestService reservationRequestService;

    @Test
    public void testAcceptReservationNullUserId() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, null, 1L, 1, 100, new TimeSlot(1630995200, 1743673600), ReservationRequestStatus.Waiting);

        NullIdException thrown = assertThrows(NullIdException.class,
                () -> reservationRequestService.createRequest(request));
        assertTrue(thrown.getMessage().contains("User id is null"));

        verify(accommodationService, times(0)).findOne(any());
        verify(userService, times(0)).findById(anyLong());
        verify(reservationService, times(0)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(0)).save(any());

    }

    @Test
    public void testAcceptReservationInvalidUserId() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, -1L, 1L, 1, 100, new TimeSlot(1630995200, 1743673600), ReservationRequestStatus.Waiting);

        Mockito.when(userService.findById(any())).thenReturn(null);

        InvalidIdException thrown = assertThrows(InvalidIdException.class,
                () -> reservationRequestService.createRequest(request));
        assertTrue(thrown.getMessage().contains("User id is invalid"));

        verify(accommodationService, times(0)).findOne(any());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(0)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(0)).save(any());

    }

    @Test
    public void testAcceptReservationNullAccommodationId() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, null, 1, 100, new TimeSlot(1630995200, 1743673600), ReservationRequestStatus.Waiting);

        Mockito.when(userService.findById(any())).thenReturn(new User());

        NullIdException thrown = assertThrows(NullIdException.class,
                () -> reservationRequestService.createRequest(request));
        assertTrue(thrown.getMessage().contains("Accommodation id is null"));

        verify(accommodationService, times(0)).findOne(any());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(0)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(0)).save(any());

    }

    @Test
    public void testAcceptReservationInvalidAccommodationId() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, -1L, 1, 100, new TimeSlot(1630995200, 1743673600), ReservationRequestStatus.Waiting);

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(null);

        InvalidIdException thrown = assertThrows(InvalidIdException.class,
                () -> reservationRequestService.createRequest(request));
        assertTrue(thrown.getMessage().contains("Accommodation id is invalid"));

        verify(accommodationService, times(1)).findOne(any());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(0)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(0)).save(any());

    }

    @Test
    public void testAcceptReservationRequestNoOverlap() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, 1L, 1, 100, new TimeSlot(1740995200, 1743673600), ReservationRequestStatus.Waiting);
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(new Reservation(2L,new User(),new Accommodation(),3,4.0,new TimeSlot(1640995200, 1643673600),ReservationStatus.Active));

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(new Accommodation());
        Mockito.when(reservationService.getActiveAccommodationReservations(any())).thenReturn(reservations);


        ReservationRequest req=reservationRequestService.createRequest(request);

        Assertions.assertNotEquals(req,null);
        verify(accommodationService, times(1)).findOne(anyLong());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(1)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(1)).save(reservationRequestArgumentCaptor.capture());
        Assertions.assertEquals(reservationRequestArgumentCaptor.getValue().getStatus(), ReservationRequestStatus.Waiting);

    }

    @Test
    public void testAcceptReservationRequestFirstDayReservationNoOverlap() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, 1L, 1, 100, new TimeSlot(1540995200, 1630995200), ReservationRequestStatus.Waiting);
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(new Reservation(2L,new User(),new Accommodation(),3,4.0,new TimeSlot(1630995200, 1643673600),ReservationStatus.Active));

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(new Accommodation());
        Mockito.when(reservationService.getActiveAccommodationReservations(any())).thenReturn(reservations);

        ReservationRequest req=reservationRequestService.createRequest(request);

        Assertions.assertNotEquals(req,null);
        verify(accommodationService, times(1)).findOne(anyLong());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(1)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(1)).save(reservationRequestArgumentCaptor.capture());
        Assertions.assertEquals(reservationRequestArgumentCaptor.getValue().getStatus(), ReservationRequestStatus.Waiting);

    }
//
    @Test
    public void testAcceptReservationRequestLastDayReservationNoOverlap() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, 1L, 1, 100, new TimeSlot(1640995200, 1743673600), ReservationRequestStatus.Waiting);
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(new Reservation(2L,new User(),new Accommodation(),3,4.0,new TimeSlot(1630995200, 1640995200),ReservationStatus.Active));

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(new Accommodation());
        Mockito.when(reservationService.getActiveAccommodationReservations(any())).thenReturn(reservations);

        ReservationRequest req=reservationRequestService.createRequest(request);

        Assertions.assertNotEquals(req,null);
        verify(accommodationService, times(1)).findOne(anyLong());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(1)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(1)).save(reservationRequestArgumentCaptor.capture());
        Assertions.assertEquals(reservationRequestArgumentCaptor.getValue().getStatus(), ReservationRequestStatus.Waiting);

    }
//
    @Test
    public void testAcceptReservationRequestReservationOverlap() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, 1L, 1, 100, new TimeSlot(1630995200, 1743673600), ReservationRequestStatus.Waiting);
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(new Reservation(2L,new User(),new Accommodation(),3,4.0,new TimeSlot(1630995200, 1640995200),ReservationStatus.Active));

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(new Accommodation());
        Mockito.when(reservationService.getActiveAccommodationReservations(any())).thenReturn(reservations);

        DatesOverlapException thrown = assertThrows(DatesOverlapException.class,
                () -> reservationRequestService.createRequest(request));
        assertTrue(thrown.getMessage().contains("Dates are overlapping with another reservation"));

        verify(accommodationService, times(1)).findOne(anyLong());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(1)).getActiveAccommodationReservations(any());
        verify(reservationRequestRepository, times(0)).save(any());

    }
//
    @Test
    public void testAcceptReservationRequestReservationNoOverlapAutoAccepting() throws Exception {
        Accommodation accommodation = new Accommodation();

        accommodation.setAutoAccepting(true);

        ReservationRequestDTO request = new ReservationRequestDTO(1L, 1L, 1L, 1, 100, new TimeSlot(1830995200, 1843673600), ReservationRequestStatus.Waiting);
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(new Reservation(2L,new User(),new Accommodation(),3,4.0,new TimeSlot(1630995200, 1640995200),ReservationStatus.Active));

        Mockito.when(userService.findById(any())).thenReturn(new User());
        Mockito.when(accommodationService.findOne(any())).thenReturn(accommodation);
        Mockito.when(reservationService.getActiveAccommodationReservations(any())).thenReturn(reservations);

        ReservationRequest reservationRequest=reservationRequestService.createRequest(request);

        Assertions.assertNotEquals(reservationRequest,null);
        verify(accommodationService, times(1)).findOne(anyLong());
        verify(userService, times(1)).findById(anyLong());
        verify(reservationService, times(1)).getActiveAccommodationReservations(any());
        verify(reservationService, times(1)).acceptReservation(any());
        verify(reservationRequestRepository, times(1)).save(reservationRequestArgumentCaptor.capture());
        Assertions.assertEquals(reservationRequestArgumentCaptor.getValue().getStatus(), ReservationRequestStatus.Accepted);

    }

}
