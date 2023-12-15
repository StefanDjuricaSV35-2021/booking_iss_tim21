package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.repository.AccommodationRepository;
import com.ISS.Booking_iss_tim21.utility.DateManipulationTools;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.dateStringToUnix;

@Service
public class AccommodationService {
    @Autowired
    AccommodationRepository repository;

    @Autowired
    AccommodationPricingService pricingService;

    public List<Accommodation> getAll(){
        return repository.findAll();
    }


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
    public List<Accommodation> getAvailableAccommodations(Long dateFrom,Long dateTo){

        List<Long> availableAccommodationsIds=pricingService.getAvailableAccommodationsIds(dateFrom,dateTo);
        List<Accommodation> availableAccommodations = new ArrayList<Accommodation>();

        for (Long id: availableAccommodationsIds) {

            availableAccommodations.add(findOne(id));

        }

        return availableAccommodations;

    }

}
