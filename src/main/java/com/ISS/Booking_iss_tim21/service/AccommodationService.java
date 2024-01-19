package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.dto.AccommodationAnnualDataDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPreviewDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationProfitDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationReservationCountDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.repository.AccommodationRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.dateStringToUnix;

@Service
public class AccommodationService {
    @Autowired
    AccommodationRepository repository;

    @Autowired
    AccommodationPricingService pricingService;

    @Autowired
    AccommodationFilterService filterService;

    @Autowired
    AccommodationDatesService dateService;

    @Autowired
    ReservationService resService;

    public List<Accommodation> getAll(){
        return repository.findAll();
    }
    public List<Accommodation> getAllEnabled(){
        return repository.findAllEnabled();
    }

    public List<Accommodation> getAllNotEnabled(){
        return repository.findAllNotEnabled();
    }

    public String getAccommodationName(Long accId){return repository.getAccommodationNameById(accId);}
    public Accommodation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(Accommodation accommodation) { repository.save(accommodation); }
    public void remove(Long id) {
        repository.deleteById(id);
    }
    public List<Accommodation> getOwnersAccommodations(Long ownerId) { return repository.getOwnersAccommodations(ownerId); }
    public List<Accommodation>getAccommodationsByLocation(String location){return repository.getAccommodationsByLocation(location);}
    public List<Accommodation>getAccommodationsByNOGuests(int noGuests){return repository.getAccommodationsByNOGuests(noGuests);}
    public List<Accommodation>getAccommodationsWithAmenities(List<Amenity> amenities){return repository.getAccommodationsByAmenitiesIn(amenities,amenities.size());}
    public List<Accommodation>getAccommodationsByType(AccommodationType type){return repository.getAccommodationsByType(type);}

    public Double getAccommodationPrice(String dateFrom,String dateTo,Integer noGuests,Long id){

        Accommodation ac=findOne(id);

        if(ac.isPerNight()){
            return pricingService.getAccommodationDateRangePrice(dateFrom,dateTo,id);
        }else{
            return pricingService.getAccommodationDateRangePrice(dateFrom,dateTo,noGuests,id);
        }

    }

    public List<Accommodation> setPrices(List<Accommodation> accs,String dateFrom,String dateTo,Integer noGuests){

        for (Accommodation ac:accs){
            ac.setPrice(getAccommodationPrice(dateFrom,dateTo,noGuests,ac.getId()));
        }
        return accs;
    }

    public List<Accommodation> getAccommodationBySearchParams(String location,Integer noGuests,String dateFrom,String dateTo ){

        Long unixDateFrom=dateStringToUnix(dateFrom);
        Long unixDateTo=dateStringToUnix(dateTo);

        List<Accommodation> availableAccommodations=getAvailableAccommodations(unixDateFrom,unixDateTo);
        List<Accommodation> accommodationAtLocation=getAccommodationsByLocation(location);
        List<Accommodation> accommodationsWithGuestRange=getAccommodationsByNOGuests(noGuests);

        List<Accommodation> validAccommodation;

        validAccommodation = availableAccommodations;
        validAccommodation.retainAll(accommodationAtLocation);
        validAccommodation.retainAll(accommodationsWithGuestRange);

        return validAccommodation;
    }

    public AccommodationPreviewDTO getAccommodationPreview(Long accId ){

        Accommodation acc=findOne(accId);
        AccommodationPreviewDTO preview= new AccommodationPreviewDTO(acc);

        return preview;
    }

    public List<Accommodation> getAvailableAccommodations(Long dateFrom,Long dateTo){

        List<Long> availableAccommodationsIds=pricingService.getAvailableAccommodationsIds(dateFrom,dateTo);
        List<Accommodation> availableAccommodations = new ArrayList<Accommodation>();

        for (Long id: availableAccommodationsIds) {
            if(hasOverlapingReservation(id,dateFrom,dateTo)){
                continue;
            }

            availableAccommodations.add(findOne(id));

        }

        return availableAccommodations;

    }

    public List<AccommodationReservationCountDTO> getOwnerAccommodationsReservationCount(Long ownerId, String dateFrom, String dateTo){

        Map<String,Integer> counter=new HashMap<>();

        List<Reservation> ownerReservation=resService.getOwnerReservationsBetweenDates(ownerId,dateFrom,dateTo);

        for(Reservation r:ownerReservation){
            if(r.getStatus()== ReservationStatus.Cancelled){continue;}
            counter.merge(r.getAccommodation().getName(), 1, Integer::sum);
        }

        List<AccommodationReservationCountDTO> accommodationReservationCountDTOS=new ArrayList<>();

        for (String key : counter.keySet()) {
            accommodationReservationCountDTOS.add(new AccommodationReservationCountDTO(key,(double)counter.get(key)));
        }

        return accommodationReservationCountDTOS;}

    public List<AccommodationProfitDTO> getOwnerAccommodationsProfit(Long ownerId, String dateFrom, String dateTo){

        Map<String,Double> profit=new HashMap<>();

        List<Reservation> ownerReservation=resService.getOwnerReservationsBetweenDates(ownerId,dateFrom,dateTo);

        for(Reservation r:ownerReservation){
            if(r.getStatus()== ReservationStatus.Cancelled){continue;}
            profit.merge(r.getAccommodation().getName(), r.getPrice(), Double::sum);
        }

        List<AccommodationProfitDTO> accommodationProfitDTOS=new ArrayList<>();

        for (String key : profit.keySet()) {
            accommodationProfitDTOS.add(new AccommodationProfitDTO(
                    key,profit.get(key)));
        }

        return accommodationProfitDTOS;
    }




    private boolean hasOverlapingReservation(Long accId,Long dateFrom,Long dateTo) {

        List<Reservation> reservations=resService.getAccommodationReservations(accId);

        for(Reservation res:reservations){

            Long resStart=res.getTimeSlot().getStartDate();
            Long resEnd=res.getTimeSlot().getEndDate();
            if((resStart < dateTo) && (resEnd > dateFrom)){
                return true;
            }
        }

        return false;

    }


    public AccommodationAnnualDataDTO getAccommodationAnnualData(Long accId,Integer year){
        String dateFrom=year+"-01-01";
        String dateTo=year+"-12-31";

        Map<Integer,Double> monthsProfit=new HashMap<>();
        Map<Integer,Integer> monthsReservations=new HashMap<>();


        List<Reservation> reservations=resService.getAccommodationReservationsBetweenDates(accId,dateFrom,dateTo);


        for(Reservation r :reservations){
            if(r.getStatus()== ReservationStatus.Cancelled){continue;}
            Date d=new Date(r.getTimeSlot().getStartDate());
            monthsProfit.merge(d.getMonth(), r.getPrice(), Double::sum);
            monthsReservations.merge(d.getMonth(),1,Integer::sum);

        }

        double[] profits = new double[12];
        int[] res = new int[12];

        for (Integer key : monthsProfit.keySet()) {
            profits[key]=monthsProfit.get(key);
            res[key]=monthsReservations.get(key);
        }

        return new AccommodationAnnualDataDTO(getAccommodationName(accId),profits,res);

    }

}
