package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationPricingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    private String token;

    @BeforeEach
    public  void login() {
        HttpHeaders headers = new HttpHeaders();
        SignInRequest user = new SignInRequest();
        user.setEmail("stefandjurica420@gmail.com");
        user.setPassword("1234");

        HttpEntity<SignInRequest> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<JWTAuthenticationResponse> responseEntity = restTemplate.exchange(
                "/api/v1/auth/signin",
                HttpMethod.POST,
                requestEntity,
                JWTAuthenticationResponse.class);
        this.token = responseEntity.getBody().getToken();

    }

    @Test
    @DisplayName("Should add accommodation pricing")
    public void addAccommodationPricingValid() {

        long startDate = 1705838400000L;// Sunday, January 21, 2024 12:00:00 PM
        long endDate = 1706011200000L;// Tuesday, January 23, 2024 12:00:00 PM
        TimeSlot timeSlot = new TimeSlot(startDate, endDate);

        AccommodationPricingDTO accommodationPricingDTO=new AccommodationPricingDTO(1L,1L,timeSlot ,123.5);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+token);

        HttpEntity<AccommodationPricingDTO> requestEntity = new HttpEntity<>(accommodationPricingDTO, headers);
        ResponseEntity<AccommodationPricingDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/pricings",
                HttpMethod.POST,
                requestEntity,
                AccommodationPricingDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }


    private static Stream<Arguments> invalidPricingProvider() {
//valid dates
        long startDate = 1705838400000L;// Sunday, January 21, 2024 12:00:00 PM
        long endDate = 1706011200000L;// Tuesday, January 23, 2024 12:00:00 PM

//invalid dates
        long existingStartDate = 1706184000000L;// Thursday, January 25, 2024 12:00:00 PM
        long existingEndDate = 1706356800000L;// Saturday, January 27, 2024 12:00:00 PM

        long reservationExistingStartDate = 1706443200000L;// Sunday, January 28, 2024 12:00:00 PM
        long reservationExistingEndDate = 1706702400000L;// Wednesday, January 31, 2024 12:00:00 PM

        return Stream.of(
                //                Missing accommodation id
                Arguments.arguments( new AccommodationPricingDTO(0L,null, new TimeSlot(startDate,endDate), 123.5)),
                //                Invalid accommodation id
                Arguments.arguments( new AccommodationPricingDTO(0L,2L, new TimeSlot(startDate,endDate), 123.5)),
                //                Invalid timeslot since it intertwines with existing accommodation pricing
                Arguments.arguments( new AccommodationPricingDTO(0L,1L, new TimeSlot(existingStartDate,existingEndDate), 123.5)),
                //                Invalid timeslot since it intertwines with reservation
                Arguments.arguments( new AccommodationPricingDTO(0L,1L, new TimeSlot(reservationExistingStartDate,reservationExistingEndDate), 123.5)),
                //                Invalid start and end date
                Arguments.arguments( new AccommodationPricingDTO(0L,1L, new TimeSlot(endDate,startDate), 123.5))
        );
    }



    @ParameterizedTest
    @MethodSource("invalidPricingProvider")
    @DisplayName("Should not add accommodation pricing when creating")
    public void addAccommodationPricingInvalid(AccommodationPricingDTO pricing) {


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+token);

        HttpEntity<AccommodationPricingDTO> requestEntity = new HttpEntity<>(pricing, headers);
        ResponseEntity<AccommodationPricingDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/pricings",
                HttpMethod.POST,
                requestEntity,
                AccommodationPricingDTO.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
