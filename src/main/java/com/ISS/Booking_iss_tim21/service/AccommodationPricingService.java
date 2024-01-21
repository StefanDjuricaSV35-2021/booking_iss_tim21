package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.dto.AccommodationPricingChangeRequestDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.repository.AccommodationPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.dateStringToUnix;

@Service
public class AccommodationPricingService {

    private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_MAC = (OS.indexOf("mac") >= 0);
    private static  Long DAY_VAL = (IS_MAC) ? 86400L : 86400000L;


    @Autowired
    AccommodationPricingRepository repository;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    ReservationService reservationService;

    public static boolean isIsMac() {
        return IS_MAC;
    }

    public List<AccommodationPricing> getAll(){
        return repository.findAll();
    }
    public AccommodationPricing findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(AccommodationPricing accommodationPricing) { repository.save(accommodationPricing); }
    public AccommodationPricing save(AccommodationPricingDTO accommodationPricingDTO) throws Exception{
        if (accommodationPricingDTO.getAccommodationId() == null) {
            throw new Exception("Accommodation id missing.");

        }

        Accommodation accommodation = accommodationService.findOne(accommodationPricingDTO.getAccommodationId());
        if (accommodation == null) {
            throw new Exception("Accommodation with this id doesn't exist.");
        }

        accommodationPricingDTO.getTimeSlot().setStartDate(accommodationPricingDTO.getTimeSlot().getStartDate()+ 3600 * AppConfig.UNIX_DIFF);
        accommodationPricingDTO.getTimeSlot().setEndDate(accommodationPricingDTO.getTimeSlot().getEndDate()+ 3600 * AppConfig.UNIX_DIFF);

        if(accommodationPricingDTO.getTimeSlot().getStartDate() >= accommodationPricingDTO.getTimeSlot().getEndDate()){
            throw new Exception("Pricing can't be created since it's start date is greater then it's end date.");
        }

        AccommodationPricing accommodationPricing = new AccommodationPricing();
        accommodationPricing.setAccommodation(accommodation);
        accommodationPricing.setTimeSlot(accommodationPricingDTO.getTimeSlot());
        accommodationPricing.setPrice(accommodationPricingDTO.getPrice());

        for (AccommodationPricing a: this.getAccommodationPricingForAccommodation(accommodation.getId())) {
            if (a.getTimeSlot().overlapsWith(accommodationPricing.getTimeSlot())){
                throw new Exception("Pricing can't be created since it's timeslot overlaps with an already existing accommodation pricing.");
            }
        }

        for (Reservation r:reservationService.getAccommodationReservations(accommodation.getId())) {
            if (r.getTimeSlot().overlapsWith(accommodationPricing.getTimeSlot())){
                throw new Exception("Pricing can't be created since it's timeslot overlaps with an already existing reservation for this accommodation.");
            }
        }

        this.save(accommodationPricing);

        return accommodationPricing;
    }
    public void remove(Long id) {
        repository.deleteById(id);
    }
    public List<AccommodationPricing> getAccommodationPricingForAccommodation(Long accommodationId) { return repository.getAccommodationPricing(accommodationId); }
    public List<Long> getAvailableAccommodationsIds(Long dateFrom, Long dateTo){
        return repository.getAccommodationIdsWithPrices(dateFrom,dateTo, DAY_VAL);
    }
    public List<TimeSlot> getAccommodationTimeSlots(Long id){return repository.getAccommodationTimeSlots(id);}

    public Double getAccommodationDateRangePrice(String dateFrom, String dateTo, Long id){

        Long unixDateFrom=dateStringToUnix(dateFrom);
        Long unixDateTo=dateStringToUnix(dateTo);

        return repository.getAccommodationTotalPriceDays(unixDateFrom,unixDateTo,id, DAY_VAL);
    }

    public Double getAccommodationDateRangePrice(String dateFrom, String dateTo, int noGuests, Long id){

        Long unixDateFrom=dateStringToUnix(dateFrom);
        Long unixDateTo=dateStringToUnix(dateTo);

        return repository.getAccommodationTotalPriceGuests(unixDateFrom,unixDateTo,id,noGuests, DAY_VAL);
    }

    public void deleteAllPricingsForAccommodation(Long accommodationId) {
        List<AccommodationPricing> pricings = repository.getAccommodationPricing(accommodationId);
        if (pricings.isEmpty()) return;

        for (AccommodationPricing p : pricings) {
            repository.deleteById(p.getId());
        }
    }


    public List<AccommodationPricing> getActiveAccommodationPricings(Long accommodationId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<AccommodationPricing> allPricings = repository.getAccommodationPricing(accommodationId);
        List<AccommodationPricing> currentPricings = new ArrayList<>();
        for (AccommodationPricing a : allPricings) {
            if (a.getTimeSlot().getEndDate() > currentUnixTimestamp)
                currentPricings.add(a);
        }
        return currentPricings;
    }

    public void savePricingFromRequest(AccommodationPricingChangeRequestDTO accommodationPricingChangeRequestDTO, Accommodation accommodation) {
        AccommodationPricing newPricing = new AccommodationPricing();
        newPricing.setAccommodation(accommodation);
        newPricing.setTimeSlot(accommodationPricingChangeRequestDTO.getTimeSlot());
        newPricing.setPrice(accommodationPricingChangeRequestDTO.getPrice());
        repository.save(newPricing);
    }
}
