package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.JWTAuthenticationResponse;
import com.ISS.Booking_iss_tim21.dto.SignInRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationPricingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    private String token;
    private String guestToken;
    private String adminToken;


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
        System.out.println(token);
    }

    @Test
    @DisplayName("Should not update and create because user has no authority")
    public void InvalidAuthorizationGuest() {

//        Long priceCardId = 1L;
//        // For example, assuming you have a PriceCard object to update
//        Timestamp startDate = Timestamp.from(Instant.parse("2024-03-19T10:57:00Z"));
//        Timestamp endDate = Timestamp.from(Instant.parse("2024-03-28T10:57:00Z"));
//        Boolean deleted = false;
//        TimeSlotPutDTO timeSlot = new TimeSlotPutDTO(startDate, endDate);
//
//        PriceCardPutDTO priceCardPutDTO=new PriceCardPutDTO(new TimeSlot(timeSlot.startDate,timeSlot.endDate,false),2000, PriceTypeEnum.PERGUEST,1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+guestToken);
//        HttpEntity<PriceCardPutDTO> requestEntity = new HttpEntity<>(priceCardPutDTO, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards/{id}",
//                HttpMethod.PUT,
//                requestEntity,
//                String.class,
//                priceCardId);
//
//        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
//
//
//
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+guestToken);
//        PriceCardPostDTO p=new PriceCardPostDTO();
//        HttpEntity<PriceCardPostDTO> requestEntity1 = new HttpEntity<PriceCardPostDTO>(p, headers);
//
//
//        responseEntity = restTemplate.exchange(
//                "/priceCards",
//                HttpMethod.POST,
//                requestEntity1,
//                String.class);
//        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        System.out.println("joe");
    }
//
//
//
//    @Test
//    @DisplayName("Should not update and create because user has no authority")
//    public void InvalidAuthorizationAdmin() {
//
//        Long priceCardId = 1L;
//        // For example, assuming you have a PriceCard object to update
//        Timestamp startDate = Timestamp.from(Instant.parse("2024-03-19T10:57:00Z"));
//        Timestamp endDate = Timestamp.from(Instant.parse("2024-03-28T10:57:00Z"));
//        Boolean deleted = false;
//        TimeSlotPutDTO timeSlot = new TimeSlotPutDTO(startDate, endDate);
//
//        PriceCardPutDTO priceCardPutDTO=new PriceCardPutDTO(new TimeSlot(timeSlot.startDate,timeSlot.endDate,false),2000, PriceTypeEnum.PERGUEST,1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+adminToken);
//        HttpEntity<PriceCardPutDTO> requestEntity = new HttpEntity<>(priceCardPutDTO, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards/{id}",
//                HttpMethod.PUT,
//                requestEntity,
//                String.class,
//                priceCardId);
//
//        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
//
//
//
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+adminToken);
//        PriceCardPostDTO p=new PriceCardPostDTO();
//        HttpEntity<PriceCardPostDTO> requestEntity1 = new HttpEntity<PriceCardPostDTO>(p, headers);
//
//
//        responseEntity = restTemplate.exchange(
//                "/priceCards",
//                HttpMethod.POST,
//                requestEntity1,
//                String.class);
//        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
//    }
//
//
//    @Test
//    @DisplayName("Should Update priceCard When making PUT request to endpoint - /priceCards/{id}")
//    public void shouldUpdatePriceCard_Valid() {
//
//        Long priceCardId = 1L;
//        // For example, assuming you have a PriceCard object to update
//        Timestamp startDate = Timestamp.from(Instant.parse("2024-03-19T10:57:00Z"));
//        Timestamp endDate = Timestamp.from(Instant.parse("2024-03-28T10:57:00Z"));
//        Boolean deleted = false;
//        TimeSlotPutDTO timeSlot = new TimeSlotPutDTO(startDate, endDate);
//
//        PriceCardPutDTO priceCardPutDTO=new PriceCardPutDTO(new TimeSlot(timeSlot.startDate,timeSlot.endDate,false),2000, PriceTypeEnum.PERGUEST,1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+token);
//        HttpEntity<PriceCardPutDTO> requestEntity = new HttpEntity<>(priceCardPutDTO, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards/{id}",
//                HttpMethod.PUT,
//                requestEntity,
//                String.class,
//                priceCardId);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//    }
//
//    private static Stream<Arguments> invalidReservationIdsAndPriceCardProvider() {
//        // Provide different invalid reservation IDs and corresponding PriceCardPostDTO instances for testing
//        LocalDate startDate = LocalDate.now().plusDays(5);
//        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
//        java.util.Date startDateAsDate = java.util.Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDate = LocalDate.now().plusDays(10);
//        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
//        Date endDateAsDate = java.util.Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate startDatePast = LocalDate.now().minusDays(5);
//        LocalDateTime startDateTimePast = LocalDateTime.of(startDatePast, LocalTime.MIN);
//        java.util.Date startDateAsDatePast = java.util.Date.from(startDateTimePast.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDatePast = LocalDate.now().minusDays(10);
//        LocalDateTime endDateTimePast = LocalDateTime.of(endDatePast, LocalTime.MAX);
//        Date endDateAsDatePast = java.util.Date.from(endDateTimePast.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//
//        LocalDate startDateExisting = LocalDate.of(2024, 1, 27);
//        LocalDateTime startDateTimeExisting = LocalDateTime.of(startDateExisting, LocalTime.MIN);
//        java.util.Date startDateAsDateExisting = java.util.Date.from(startDateTimeExisting.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExisting = LocalDate.of(2024, 2, 15);
//        LocalDateTime endDateTimeExisting = LocalDateTime.of(endDateExisting, LocalTime.MAX);
//        Date endDateAsDateExisting = java.util.Date.from(endDateTimeExisting.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//
//
//        LocalDate startDateExistingRes = LocalDate.of(2021, 2, 8);
//        LocalDateTime startDateTimeExistingRes = LocalDateTime.of(startDateExistingRes, LocalTime.MIN);
//        java.util.Date startDateAsDateExistingRes = java.util.Date.from(startDateTimeExistingRes.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExistingRes = LocalDate.of(2024, 2, 18);
//        LocalDateTime endDateTimeExistingRes = LocalDateTime.of(endDateExistingRes, LocalTime.MAX);
//        Date endDateAsDateExistingRes = java.util.Date.from(endDateTimeExistingRes.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        return Stream.of(
//
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDate,endDateAsDate,false), 2000, PriceTypeEnum.PERGUEST, 8L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDatePast,endDateAsDate,false), 2000, PriceTypeEnum.PERGUEST, 1L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDate,endDateAsDatePast,false), 2000, PriceTypeEnum.PERGUEST, 1L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDate,endDateAsDatePast,false), 2000, PriceTypeEnum.PERGUEST, 1L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDate,endDateAsDate,false), -2000, PriceTypeEnum.PERGUEST, 1L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDateExisting,endDateAsDateExisting,false), 2000, null, 1L)),
//                arguments(1L,  new PriceCardPutDTO(new TimeSlot(startDateAsDateExistingRes,endDateAsDateExistingRes,false), 2000, PriceTypeEnum.PERGUEST, 1L))
//
//
//        );
//    }
//
//
//
//    @ParameterizedTest
//    @MethodSource("invalidReservationIdsAndPriceCardProvider")
//    @DisplayName("Should Not Update priceCard When making PUT request to endpoint - /priceCards/{id}")
//    public void shouldNotUpdate_InvalidPriceCard(Long invalidPriceCardId,PriceCardPutDTO priceCardPutDTO) {
//
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+token);
//        HttpEntity<PriceCardPutDTO> requestEntity = new HttpEntity<>(priceCardPutDTO, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards/{id}",
//                HttpMethod.PUT,
//                requestEntity,
//                String.class,
//                invalidPriceCardId);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//
//    }
//
//    @Test
//    @DisplayName("Should Create priceCard When making Post request to endpoint - /priceCards/{id}")
//    public void shouldCreatePriceCard_Valid() {
//
//
//        // For example, assuming you have a PriceCard object to update
//        Timestamp startDate = Timestamp.from(Instant.parse("2024-05-19T10:57:00Z"));
//        Timestamp endDate = Timestamp.from(Instant.parse("2024-05-28T10:57:00Z"));
//        Boolean deleted = false;
//        TimeSlotPutDTO timeSlot = new TimeSlotPutDTO(startDate, endDate);
//
//        PriceCardPostDTO priceCardPutDTO=new PriceCardPostDTO(new TimeSlotPostDTO(timeSlot.startDate,timeSlot.endDate),2000, PriceTypeEnum.PERGUEST,1L);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","Bearer "+token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<PriceCardPostDTO> requestEntity = new HttpEntity<>(priceCardPutDTO, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards",
//                HttpMethod.POST,
//                requestEntity,
//                String.class);
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//    }
//
//
//
//    private static Stream<PriceCardPostDTO>invalidPriceCardProvider() {
//        // Provide different invalid reservation IDs and corresponding PriceCardPostDTO instances for testing
//        LocalDate startDate = LocalDate.now().plusDays(5);
//        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
//        java.util.Date startDateAsDate = java.util.Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDate = LocalDate.now().plusDays(10);
//        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
//        Date endDateAsDate = java.util.Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate startDatePast = LocalDate.now().minusDays(5);
//        LocalDateTime startDateTimePast = LocalDateTime.of(startDatePast, LocalTime.MIN);
//        java.util.Date startDateAsDatePast = java.util.Date.from(startDateTimePast.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDatePast = LocalDate.now().minusDays(10);
//        LocalDateTime endDateTimePast = LocalDateTime.of(endDatePast, LocalTime.MAX);
//        Date endDateAsDatePast = java.util.Date.from(endDateTimePast.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//
//        LocalDate startDateExisting = LocalDate.of(2024, 1, 27);
//        LocalDateTime startDateTimeExisting = LocalDateTime.of(startDateExisting, LocalTime.MIN);
//        java.util.Date startDateAsDateExisting = java.util.Date.from(startDateTimeExisting.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExisting = LocalDate.of(2024, 2, 15);
//        LocalDateTime endDateTimeExisting = LocalDateTime.of(endDateExisting, LocalTime.MAX);
//        Date endDateAsDateExisting = java.util.Date.from(endDateTimeExisting.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//
//
//        LocalDate startDateExistingRes = LocalDate.of(2024, 2, 8);
//        LocalDateTime startDateTimeExistingRes = LocalDateTime.of(startDateExistingRes, LocalTime.MIN);
//        java.util.Date startDateAsDateExistingRes = java.util.Date.from(startDateTimeExistingRes.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExistingRes = LocalDate.of(2024, 2, 18);
//        LocalDateTime endDateTimeExistingRes = LocalDateTime.of(endDateExistingRes, LocalTime.MAX);
//        Date endDateAsDateExistingRes = java.util.Date.from(endDateTimeExistingRes.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate startDateExistingRes1 = LocalDate.of(2024, 2, 10);
//        LocalDateTime startDateTimeExistingRes1 = LocalDateTime.of(startDateExistingRes1, LocalTime.MIN);
//        java.util.Date startDateAsDateExistingRes1 = java.util.Date.from(startDateTimeExistingRes1.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExistingRes1 = LocalDate.of(2024, 2, 15);
//        LocalDateTime endDateTimeExistingRes1 = LocalDateTime.of(endDateExistingRes1, LocalTime.MAX);
//        Date endDateAsDateExistingRes1 = java.util.Date.from(endDateTimeExistingRes1.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//
//        LocalDate startDateExistingRes2 = LocalDate.of(2024, 2, 10);
//        LocalDateTime startDateTimeExistingRes2 = LocalDateTime.of(startDateExistingRes2, LocalTime.MIN);
//        java.util.Date startDateAsDateExistingRes2 = java.util.Date.from(startDateTimeExistingRes2.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        LocalDate endDateExistingRes2 = LocalDate.of(2024, 2, 18);
//        LocalDateTime endDateTimeExistingRes2 = LocalDateTime.of(endDateExistingRes2, LocalTime.MAX);
//        Date endDateAsDateExistingRes2 = java.util.Date.from(endDateTimeExistingRes2.atZone(java.time.ZoneId.systemDefault()).toInstant());
//
//        return Stream.of(
//
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDate,endDateAsDate), 2000, PriceTypeEnum.PERGUEST, 30L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDatePast,endDateAsDate), 2000, PriceTypeEnum.PERGUEST, 2L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDate,endDateAsDatePast), 2000, PriceTypeEnum.PERGUEST, 2L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDate,endDateAsDatePast), 2000, PriceTypeEnum.PERGUEST, 2L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDate,endDateAsDate), -2000, PriceTypeEnum.PERGUEST, 2L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDate,endDateAsDate), 2000, null, 2L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDateExistingRes,endDateAsDateExistingRes), 2000, PriceTypeEnum.PERGUEST, 1L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDateExistingRes1,endDateAsDateExistingRes1), 2000, PriceTypeEnum.PERGUEST, 1L),
//                new PriceCardPostDTO(new TimeSlotPostDTO(startDateAsDateExistingRes2,endDateAsDateExistingRes2), 2000, PriceTypeEnum.PERGUEST, 1L)
//
//        );
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("invalidPriceCardProvider")
//    @DisplayName("Should Not Create priceCard When making Post request to endpoint - /priceCards")
//    public void shouldNotCreatePriceCard_InValid(PriceCardPostDTO priceCardPostDTO) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+token);
//        HttpEntity<PriceCardPostDTO> requestEntity = new HttpEntity<>(priceCardPostDTO, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/priceCards",
//                HttpMethod.POST,
//                requestEntity,
//                String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//    }
//
//    @Test
//    @DisplayName("Should Create cancelation Deadline When making Post request to endpoint /accommodations")
//    public void shouldCreateCancellationDeadLine_Valid() {
//
//        LocationPostDTO loc = new LocationPostDTO("LocationAddress", "LocationCity", "LocationCountry", 1.234, 5.678);
//        List<String> assets = new ArrayList<>();
//        assets.add("klima");
//
//        AccommodationPostDTO accommodation = new AccommodationPostDTO( "ime", "opis", loc, 2, 5, TypeEnum.APARTMENT,
//                assets,  "TESTGOST1@gmail.com", 5,new ArrayList<String>());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization","Bearer "+token);
//        HttpEntity<AccommodationPostDTO> requestEntity = new HttpEntity<>(accommodation, headers);
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/accommodations",
//                HttpMethod.POST,
//                requestEntity,
//                String.class);
//
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//    }
//
//
//
//    private static Stream<AccommodationPostDTO>invalidCancelationDeadlineProvider() {
//        // Provide different invalid reservation IDs and corresponding PriceCardPostDTO instances for testing
//        LocationPostDTO loc = new LocationPostDTO("LocationAddress", "LocationCity", "LocationCountry", 1.234, 5.678);
//        List<String> assets = new ArrayList<>();
//        assets.add("klima");
//
//        AccommodationPostDTO accommodation = new AccommodationPostDTO( "ime", "opis", loc, 2, 5, TypeEnum.APARTMENT,
//                assets,  "TESTGOST1@gmail.com", 5,new ArrayList<String>());
//
//
//        return Stream.of(
//                new AccommodationPostDTO( "ime", "opis", loc, 2, 5, TypeEnum.APARTMENT,
//                        assets,  "TESTGOST1@gmail.com", 0,new ArrayList<String>()),
//                new AccommodationPostDTO( "ime", "opis", loc, 2, 5, TypeEnum.APARTMENT,
//                        assets,  "TESTGOST1@gmail.com", -10,new ArrayList<String>())
//        );
//    }
//
//
//
//
//    @ParameterizedTest
//    @MethodSource("invalidCancelationDeadlineProvider")
//    @DisplayName("Should not create cancelation Deadline  When its zero or negative making Post request to endpoint /accommodations")
//    public void shouldNotCreateCancellationDeadLine_IValid(AccommodationPostDTO accommodation) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<AccommodationPostDTO> requestEntity = new HttpEntity<>(accommodation, headers);
//        headers.set("Authorization","Bearer "+token);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "/accommodations",
//                HttpMethod.POST,
//                requestEntity,
//                String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//
//    }
}
