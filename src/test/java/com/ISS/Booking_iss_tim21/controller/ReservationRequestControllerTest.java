package com.ISS.Booking_iss_tim21.controller;
import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationRequestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    private String token;

    @BeforeEach
    public void login() {
        HttpHeaders headers = new HttpHeaders();
        SignInRequest user = new SignInRequest();
        user.setEmail("owner@example.com");
        user.setPassword("admin");

        HttpEntity<SignInRequest> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<JWTAuthenticationResponse> responseEntity = restTemplate.exchange(
                "/api/v1/auth/signin",
                HttpMethod.POST,
                requestEntity,
                JWTAuthenticationResponse.class);
        this.token = responseEntity.getBody().getToken();

    }

    @Test
    @DisplayName("Test with invalid request id, should return bad request")
    public void testUpdateRequestShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setId(-1L);
        requestDTO.setStatus(ReservationRequestStatus.Accepted);
        requestDTO.setPrice(200.0);
        requestDTO.setTimeSlot(new TimeSlot(1640995200, 1643673600));
        requestDTO.setUserId(1L);
        requestDTO.setAccommodationId(1L);
        requestDTO.setGuestsNumber(3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);

        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/reservationRequests",
                HttpMethod.PUT,
                requestEntity,
                ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test with invalid user id, should return bad request")
    public void testUpdateRequestInvalidUserShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setStatus(ReservationRequestStatus.Accepted);
        requestDTO.setPrice(200.0);
        requestDTO.setTimeSlot(new TimeSlot(1640995200, 1643673600));
        requestDTO.setUserId(-1L);
        requestDTO.setAccommodationId(1L);
        requestDTO.setGuestsNumber(3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);

        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/reservationRequests",
                HttpMethod.PUT,
                requestEntity,
                ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test with invalid accommodation id, should return bad request")
    public void testUpdateRequestInvalidAccommodationShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setStatus(ReservationRequestStatus.Accepted);
        requestDTO.setPrice(200.0);
        requestDTO.setTimeSlot(new TimeSlot(1640995200, 1643673600));
        requestDTO.setUserId(1L);
        requestDTO.setAccommodationId(-1L);
        requestDTO.setGuestsNumber(3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);

        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/reservationRequests",
                HttpMethod.PUT,
                requestEntity,
                ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test with valid inputs, should return ok")
    public void testUpdateRequestShouldReturnOk() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setStatus(ReservationRequestStatus.Waiting);
        requestDTO.setPrice(200.0);
        requestDTO.setTimeSlot(new TimeSlot(1640995200, 1643673600));
        requestDTO.setUserId(1L);
        requestDTO.setAccommodationId(1L);
        requestDTO.setGuestsNumber(3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);

        HttpEntity<ReservationRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/reservationRequests",
                HttpMethod.PUT,
                requestEntity,
                ReservationRequestDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
