package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AccommodationPricingRepositoryTest {
    @Autowired
    private AccommodationPricingRepository accommodationPricingRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void shouldSaveAccommodationPricing() {

        long startDate = 1705838400000L;
        long endDate = 1706011200000L;
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);

        AccommodationPricing accommodationPricing = new AccommodationPricing();
        accommodationPricing.setAccommodation(createSampleAccommodation());
        accommodationPricing.setTimeSlot(timeSlot);
        accommodationPricing.setPrice(123.5);

        AccommodationPricing savedAccommodationPricing = accommodationPricingRepository.save(accommodationPricing);

        assertThat(savedAccommodationPricing).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodationPricing);
    }

    @Test
    public void shouldSaveAccommodationPricingSql() {
        Optional<AccommodationPricing> test = accommodationPricingRepository.findById(1L);
        assertThat(test).isNotEmpty();
    }

    public static Accommodation createSampleAccommodation() {
        User owner = new User(/* Set properties for the owner */);
        owner.setId(1L);
        owner.setCity("Berlin");
        owner.setCountry("Germany");
        owner.setEmail("stefandjurica420@gmail.com");
        owner.setName("Bob");
        owner.setPassword("1234");
        owner.setPhone("+49 30 9876 5432");
        owner.setStreet("789 Oak St");
        owner.setSurname("Jones");
        owner.setRole(Role.OWNER);
        owner.setEnabled(true);
        owner.setBlocked(false);

        User user = new User(/* Set properties for the owner */);
        user.setId(2L);
        user.setCity("Berlin");
        user.setCountry("Germany");
        user.setEmail("stefandjurica69@gmail.com");
        user.setName("Joe");
        user.setPassword("1234");
        user.setPhone("+49 30 9876 5432");
        user.setStreet("789 Oak St");
        user.setSurname("Bones");
        user.setRole(Role.GUEST);
        user.setEnabled(true);
        user.setBlocked(false);


        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuestsNumber(3);
        reservation.setPrice(747);
        reservation.setStatus(ReservationStatus.Active);
        reservation.setTimeSlot(new TimeSlot(1706443200000L,1706702400000L));
        reservation.setUser(user);


        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setOwner(owner);
        accommodation.setName("Cozy Cottage");
        accommodation.setType(AccommodationType.Room);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(4);
        accommodation.setDescription("A lovely cottage in the woods");
        accommodation.setLocation("Beograd");
        accommodation.setEnabled(true);
        accommodation.setPerNight(false);
        accommodation.setAutoAccepting(false);
        accommodation.setDaysForCancellation(7);

        reservation.setAccommodation(accommodation);


        return accommodation;
    }
}