package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.repository.AccommodationPricingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccommodationPricingServiceTest {
    @MockBean
    private AccommodationPricingRepository accommodationPricingRepository;

    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private AccommodationPricingService accommodationPricingService;

    @Captor
    private ArgumentCaptor<AccommodationPricing> accommodationPricingCaptor;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(accommodationPricingRepository.save(any(AccommodationPricing.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Accommodation accommodation = new Accommodation();

        when(accommodationService.findOne(1L)).thenReturn(createSampleAccommodation());
        when(accommodationService.findOne(2L)).thenReturn(null);

        List<AccommodationPricing> mockedList = new ArrayList<>();
        AccommodationPricing mockPricing = new AccommodationPricing(1L,createSampleAccommodation(),new TimeSlot(1706184000000L,1706356800000L),747);
        mockedList.add(mockPricing);
        when(accommodationPricingRepository.getAccommodationPricing(1L)).thenReturn(mockedList);


        List<Reservation> mockedListReservation = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        Reservation mockReservation = new Reservation(1L,user,createSampleAccommodation(),1,747,new TimeSlot(1706443200000L,1706702400000L), ReservationStatus.Active);
        mockedListReservation.add(mockReservation);
        when(reservationService.getAccommodationReservations(1L)).thenReturn(mockedListReservation);

    }

    @Test
    @DisplayName("Should create valid accommodation pricing")
    public void shouldCreateValidAccommodationPricing() throws Exception {
        // Arrange
        long startDate = 1705838400000L;
        long endDate = 1706011200000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, 1L, timeSlot, 123.5);

        // Act
        accommodationPricingService.save(accommodationPricingDTO);

        // Assert
        verify(accommodationPricingRepository, times(1)).save(accommodationPricingCaptor.capture());
        verify(accommodationService, times(1)).findOne(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, times(1)).getAccommodationPricing(accommodationPricingDTO.getAccommodationId());
        verify(reservationService, times(1)).getAccommodationReservations(accommodationPricingDTO.getAccommodationId());

        AccommodationPricing savedAccommodationPricing = accommodationPricingCaptor.getValue();

        assertEquals(accommodationPricingDTO.getAccommodationId(), savedAccommodationPricing.getAccommodation().getId());
    }

    @Test
    @DisplayName("Should not create valid accommodation pricing since the accommodation id is null")
    public void shouldNotCreateValidAccommodationPricing_NullAccommodationId() throws Exception {
        // Arrange
        long startDate = 1705838400000L;
        long endDate = 1706011200000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, null, timeSlot, 123.5);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            accommodationPricingService.save(accommodationPricingDTO);
        });

        // Assert that the exception message is as expected
        String expectedMessage = "Accommodation id missing.";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

        // Assert
        verify(accommodationPricingRepository, never()).save(accommodationPricingCaptor.capture());
        verify(accommodationService, never()).findOne(anyLong());
        verify(accommodationPricingRepository, never()).getAccommodationPricing(anyLong());
        verify(reservationService, never()).getAccommodationReservations(anyLong());
    }

    @Test
    @DisplayName("Should not create valid accommodation pricing since the accommodation with the given id doesn't exist")
    public void shouldNotCreateValidAccommodationPricing_NullAccommodation() throws Exception {
        // Arrange
        long startDate = 1705838400000L;
        long endDate = 1706011200000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, 2L, timeSlot, 123.5);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            accommodationPricingService.save(accommodationPricingDTO);
        });

        // Assert that the exception message is as expected
        String expectedMessage = "Accommodation with this id doesn't exist.";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

        // Assert
        verify(accommodationService, times(1)).findOne(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, never()).save(accommodationPricingCaptor.capture());
        verify(accommodationPricingRepository, never()).getAccommodationPricing(anyLong());
        verify(reservationService, never()).getAccommodationReservations(anyLong());
    }

    @Test
    @DisplayName("Should not create valid accommodation pricing since the end date is before the start date")
    public void shouldNotCreateValidAccommodationPricing_WrongDates() throws Exception {
        // Arrange
        long endDate = 1705838400000L;
        long startDate = 1706011200000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, 1L, timeSlot, 123.5);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            accommodationPricingService.save(accommodationPricingDTO);
        });

        // Assert that the exception message is as expected
        String expectedMessage = "Pricing can't be created since it's start date is greater then it's end date.";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

        // Assert
        verify(accommodationService, times(1)).findOne(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, never()).save(accommodationPricingCaptor.capture());
        verify(accommodationPricingRepository, never()).getAccommodationPricing(anyLong());
        verify(reservationService, never()).getAccommodationReservations(anyLong());
    }

    @Test
    @DisplayName("Should not create valid accommodation pricing since it overlaps with another pricing for this accommodation")
    public void shouldNotCreateValidAccommodationPricing_OverlapsPricing() throws Exception {
        // Arrange
        long startDate = 1706184000000L;
        long endDate = 1706356800000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, 1L, timeSlot, 123.5);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            accommodationPricingService.save(accommodationPricingDTO);
        });

        // Assert that the exception message is as expected
        String expectedMessage = "Pricing can't be created since it's timeslot overlaps with an already existing accommodation pricing.";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

        // Assert
        verify(accommodationService, times(1)).findOne(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, times(1)).getAccommodationPricing(accommodationPricingDTO.getAccommodationId());
        verify(reservationService, never()).getAccommodationReservations(anyLong());
        verify(accommodationPricingRepository, never()).save(accommodationPricingCaptor.capture());
    }

    @Test
    @DisplayName("Should not create valid accommodation pricing since it overlaps with another reservations timeslot for this accommodation")
    public void shouldNotCreateValidAccommodationPricing_OverlapsReservation() throws Exception {
        // Arrange
        long startDate = 1706443200000L;
        long endDate = 1706702400000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);
        AccommodationPricingDTO accommodationPricingDTO = new AccommodationPricingDTO(1L, 1L, timeSlot, 123.5);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> {
            accommodationPricingService.save(accommodationPricingDTO);
        });

        // Assert that the exception message is as expected
        String expectedMessage = "Pricing can't be created since it's timeslot overlaps with an already existing reservation for this accommodation.";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

        // Assert
        verify(accommodationService, times(1)).findOne(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, times(1)).getAccommodationPricing(accommodationPricingDTO.getAccommodationId());
        verify(reservationService, times(1)).getAccommodationReservations(accommodationPricingDTO.getAccommodationId());
        verify(accommodationPricingRepository, never()).save(accommodationPricingCaptor.capture());
    }

    public static Accommodation createSampleAccommodation() {
        User owner = new User(/* Set properties for the owner */);
        owner.setId(2L);
        List<Amenity> amenities = Arrays.asList(Amenity.TV, Amenity.WiFi);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setOwner(owner);
        accommodation.setName("Sample Accommodation");
        accommodation.setType(AccommodationType.Room);
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(10);
        accommodation.setDescription("A wonderful place to stay");
        accommodation.setLocation("Sample Location");
        accommodation.setEnabled(true);
        accommodation.setPerNight(true);
        accommodation.setAutoAccepting(true);
        accommodation.setAmenities(amenities);
        accommodation.setPhotos(Arrays.asList("photo1.jpg", "photo2.jpg"));
        accommodation.setDaysForCancellation(7);

        return accommodation;
    }
}
