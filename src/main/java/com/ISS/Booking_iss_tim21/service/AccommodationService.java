package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.repository.AccommodationRepository;
import org.hibernate.validator.cfg.context.PropertyTarget;
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
    public List<Accommodation>getAccommodationsByTypes(List<AccommodationType> types){return repository.getAccommodationsByTypeIn(types);}


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

    public List<Accommodation> filterAccommodations(List<Accommodation> accommodations, String filterUrlParam){

        String[] filterParams=filterUrlParam.split(";");

        List<Amenity> amenities=extractAmenities(filterParams);
        List<AccommodationType> types=extractTypes(filterParams);

        if(!amenities.isEmpty()){
            List<Accommodation> accommodationsWithAmenities=getAccommodationsWithAmenities(amenities);
            accommodations.retainAll(accommodationsWithAmenities);
        }

        if(!types.isEmpty()){
            List<Accommodation> accommodationsWithType=getAccommodationsByTypes(types);
            accommodations.retainAll(accommodationsWithType);
        }

        return accommodations;
    }

    List<Amenity> extractAmenities(String[] filterParams){

        List<Amenity> amenities=new ArrayList<Amenity>();

        for (String param:filterParams) {

            String[] typeAndVal=param.split("=");

            if(typeAndVal[0].equals("Amenity")){

                int amenityValue=Integer.parseInt(typeAndVal[1]);
                Amenity amenity=Amenity.values()[amenityValue];
                amenities.add(amenity);

            }

        }
        return amenities;
    }

    List<AccommodationType> extractTypes(String[] filterParams){

        List<AccommodationType> types=new ArrayList<AccommodationType>();

        for (String param:filterParams) {

            String[] typeAndVal=param.split("=");

            if(typeAndVal[0].equals("PropertyType")){

                int typeValue=Integer.parseInt(typeAndVal[1]);
                AccommodationType type=AccommodationType.values()[typeValue];
                types.add(type);

            }

        }
        return types;
    }


}
