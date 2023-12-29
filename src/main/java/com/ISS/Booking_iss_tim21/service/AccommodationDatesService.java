package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.datesToTimeslot;

@Service
public class AccommodationDatesService {

    @Autowired
    AccommodationPricingService pricingService;

    @Autowired
    ReservationService resService;

    public List<TimeSlot> getAccommodationAvaiableDates(Long id){

        List<TimeSlot> datesWithPrices = pricingService.getAccommodationTimeSlots(id);
        List<Reservation> reservations=resService.getActiveAccommodationReservations(id);
        if(reservations.isEmpty()){
            return datesWithPrices;
        }else {
            return removeReservationDates(datesWithPrices, reservations);
        }


    }

    List<TimeSlot> removeReservationDates(List<TimeSlot> timeSlots,List<Reservation> reservations){

        List<TimeSlot> availableDates=new ArrayList<>();

        for(TimeSlot ts:timeSlots){
            LocalDate startDate=null;
            LocalDate tsStart=new LocalDate((ts.getStartDate()* AppConfig.UNIX_DIFF));
            LocalDate tsEnd=new LocalDate((ts.getEndDate()* AppConfig.UNIX_DIFF));

            for(LocalDate d=tsStart;!d.isEqual(tsEnd);d=d.plusDays(1)){

                boolean isAvaiable= isDayReserved(d,reservations);

                if(!isAvaiable){

                    if(startDate!=null){
                        TimeSlot newTs=datesToTimeslot(tsStart,d);
                        availableDates.add(newTs);
                        startDate=null;
                    }

                }else{
                    if(startDate==null){
                        startDate=d;
                    }
                }

            }

            if(startDate!=null){
                TimeSlot newTs=datesToTimeslot(startDate,tsEnd);
                availableDates.add(newTs);
            }

        }

        return availableDates;

    }

    boolean isDayReserved(LocalDate d, List<Reservation> reservations){

        for(Reservation r:reservations){
            LocalDate resStart=new LocalDate(r.getTimeSlot().getStartDate()*AppConfig.UNIX_DIFF);
            LocalDate resEnd=new LocalDate(r.getTimeSlot().getEndDate()* AppConfig.UNIX_DIFF);

            if((d.isEqual(resStart)||d.isAfter(resStart))&&d.isBefore(resEnd)){
                return false;

            }

        }

        return true;

    }

}
