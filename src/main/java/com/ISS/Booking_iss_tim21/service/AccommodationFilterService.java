package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.AccommodationPreviewDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.utility.UrlFilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationFilterService {

    @Autowired
    AccommodationService accService;

    public List<Accommodation> filterAccommodations(List<Accommodation> accs, String urlParams){

        if(urlParams==null){
            return accs;
        }
        UrlFilterParams urlFilterParams=new UrlFilterParams();
        urlFilterParams.setParams(urlParams);

        accs=filterAccommodationsByAmenities(accs,urlFilterParams.getAmenities());

        accs=filterAccommodationsByType(accs,urlFilterParams.getType());

        accs=filterAccommodationsByPriceRange(accs,urlFilterParams.getMinPrice(), urlFilterParams.getMaxPrice());

        return accs;
    }

    List<Accommodation> filterAccommodationsByAmenities(List<Accommodation> accs,List<Amenity> amm){

        if(!amm.isEmpty()){
            List<Accommodation> accommodationsWithAmenities=accService.getAccommodationsWithAmenities(amm);
            accs.retainAll(accommodationsWithAmenities);
        }

        return accs;

    }
    List<Accommodation> filterAccommodationsByType(List<Accommodation> accs,AccommodationType t){

        if(t!=null){
            List<Accommodation> accommodationsWithType=accService.getAccommodationsByType(t);
            accs.retainAll(accommodationsWithType);
        }

        return accs;

    }

    public List<Accommodation> filterAccommodationsByPriceRange(List<Accommodation> accs, Integer minPrice, Integer maxPrice){

        accs=filterAccommodationsByMinPrice(accs,minPrice);
        accs=filterAccommodationsByMaxPrice(accs,maxPrice);

        return accs;

    }

    List<Accommodation> filterAccommodationsByMinPrice(List<Accommodation> accs, Integer minPrice){

        List<Accommodation> filtered=new ArrayList<>();

        if(minPrice!=null){

            for (Accommodation ac :accs){

                if(ac.getPrice()>minPrice){

                    filtered.add(ac);
                }
            }
            return filtered;

        }else{

            return accs;

        }

    }

    List<Accommodation> filterAccommodationsByMaxPrice(List<Accommodation> accs,Integer maxPrice){

        List<Accommodation> filtered=new ArrayList<>();

        if(maxPrice!=null){

            for (Accommodation ac :accs){

                if(ac.getPrice()<maxPrice){

                    filtered.add(ac);
                }
            }
            return filtered;

        }else{

            return accs;

        }
    }


}
