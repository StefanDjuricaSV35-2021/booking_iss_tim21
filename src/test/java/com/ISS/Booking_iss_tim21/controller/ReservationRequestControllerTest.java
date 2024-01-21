package com.ISS.Booking_iss_tim21.controller;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import com.ISS.Booking_iss_tim21.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(controllers = ReservationRequestController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReservationRequestControllerTest {
    final String END_POINT_PATH="/api/v1/auth/reservationRequests";
    private final ObjectMapper objectMapper=new ObjectMapper();

    // Zbog main klase

    @MockBean
    private JWTService yourService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ReservationService reservationService;

    //-------------------------------

    @MockBean
    private ReservationRequestService requestService;
    @MockBean
    private UserService userService;
    @MockBean
    private AccommodationService accommodationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ROLE_GUEST"})
    public void test_add_null_user_id_should_return_400_bad_request() throws Exception {
        ReservationRequestDTO req=new ReservationRequestDTO();
        req.setId(2L);
        req.setStatus(ReservationRequestStatus.Accepted);
        req.setPrice(200.0);
        req.setTimeSlot(new TimeSlot());
        req.setUserId(3L);
        req.setAccommodationId(2L);
        req.setGuestsNumber(3);

        String jsonRequest = objectMapper.writeValueAsString(req);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(END_POINT_PATH).with(csrf()) //<- kod POST mora csrf
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;

    }
    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_OWNER", "ROLE_GUEST"})
    void test_get_all_reservation_requests() throws Exception {
        ArrayList<ReservationRequest> reqs=new ArrayList<>();
        ReservationRequest req=new ReservationRequest(1L,new User(),new Accommodation(),3,3.0,new TimeSlot(),ReservationRequestStatus.Accepted);
        reqs.add(req);
        when(requestService.getAll()).thenReturn(reqs);

        ResultActions action=mockMvc.perform(MockMvcRequestBuilders
                        .get(END_POINT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        ReservationRequestDTO[] results=objectMapper.readValue(action.andReturn().getResponse().getContentAsString(),ReservationRequestDTO[].class);
        assertEquals(results.length,1);
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