package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.repository.AccommodationRepository;
import com.ISS.Booking_iss_tim21.utility.UrlFilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.ISS.Booking_iss_tim21.utility.DateManipulationTools.dateStringToUnix;

@Service
public class AccommodationService {
    @Autowired
    AccommodationRepository repository;

    @Autowired
    AccommodationPricingService pricingService;

    @Autowired
    AccommodationFilterService filterService;

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
    public List<Accommodation>getAccommodationsWithAmenities(List<Amenity> amenities){return repository.getAccommodationsByAmenitiesIn(amenities,amenities.size());}
    public List<Accommodation>getAccommodationsByType(AccommodationType type){return repository.getAccommodationsByType(type);}

    public List<Accommodation> setPrices(List<Accommodation> accs,String dateFrom,String dateTo){

        for (Accommodation ac:accs){
            ac.setPrice(pricingService.getAccommodationDateRangePrice(dateFrom,dateTo,ac.getId()));
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

    public List<Accommodation> getAvailableAccommodations(Long dateFrom,Long dateTo){

        List<Long> availableAccommodationsIds=pricingService.getAvailableAccommodationsIds(dateFrom,dateTo);
        List<Accommodation> availableAccommodations = new ArrayList<Accommodation>();

        for (Long id: availableAccommodationsIds) {

            availableAccommodations.add(findOne(id));

        }

        return availableAccommodations;

    }


}
