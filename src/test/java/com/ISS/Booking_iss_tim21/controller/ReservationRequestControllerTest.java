package com.ISS.Booking_iss_tim21.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationRequestService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ReservationRequestController.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReservationRequestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    final String END_POINT_PATH="http://localhost:8080/api/v1/auth/reservationRequests";
    private final ObjectMapper objectMapper=new ObjectMapper();
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private ReservationRequestService requestService;
    @MockBean
    private UserService userService;
    @MockBean
    private AccommodationService accommodationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        ReservationRequestDTO req=new ReservationRequestDTO();
        req.setId(2L);
        req.setStatus(ReservationRequestStatus.Accepted);
        req.setPrice(200.0);
        req.setTimeSlot(new TimeSlot());
        req.setUserId(3L);
        req.setAccommodationId(2L);
        req.setGuestsNumber(3);

        String jsonRequest = objectMapper.writeValueAsString(req);

        mockMvc.perform(post(END_POINT_PATH).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }
    @Test
    void getReservationRequests() {
    }

    @Test
    void getReservationRequest() {
    }

    @Test
    void createReservationRequest() {
    }

    @Test
    void updateReservationRequest() {
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_OWNER", "ROLE_GUEST"})
    public void testUpdateRequestShouldReturnBadRequest() throws Exception {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO();
        requestDTO.setId(2L);
        requestDTO.setStatus(ReservationRequestStatus.Accepted);
        requestDTO.setPrice(200.0);
        requestDTO.setTimeSlot(new TimeSlot());
        requestDTO.setUserId(1L);
        requestDTO.setAccommodationId(1L);
        requestDTO.setGuestsNumber(3);

        String jsonRequest = objectMapper.writeValueAsString(requestDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/reservationRequests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .characterEncoding("utf-8"))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
    }

    @Test
    void deleteReservationRequest() {
    }

    @Test
    void getUsersReservationRequests() {
    }

    @Test
    void getOwnerReservationRequests() {
    }

    @Test
    void getCurrentReservationRequests() {
    }
}