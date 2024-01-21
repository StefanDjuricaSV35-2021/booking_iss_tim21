package com.ISS.Booking_iss_tim21.utility;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;

import java.util.ArrayList;
import java.util.List;

public class UrlFilterParams {
    

    List<Amenity> amenities=new ArrayList<>();
    AccommodationType type=null;
    Double minPrice=null;
    Double maxPrice=null;

    public UrlFilterParams() {
    }

    public void setParams(String url){

        String[] filterParams=url.split(";");

        for (String param:filterParams) {

            String[] typeAndVal=param.split("=");
            String type=typeAndVal[0];
            String val=typeAndVal[1];

            switch(type) {
                case "Amenity": {
                    this.amenities.add(Amenity.values()[Integer.parseInt(val)]);
                    break;
                }
                case "AccommodationType": {
                    this.type=AccommodationType.values()[Integer.parseInt(val)];
                    break;
                }
                case "MinPrice": {
                    this.minPrice=Double.parseDouble(val);
                    break;
                }
                case "MaxPrice": {
                    this.maxPrice=Double.parseDouble(val);
                    break;
                }
                default: {
                    break;
                }
            }

        }


    }


    public List<Amenity> getAmenities() {
        return amenities;
    }

    public AccommodationType getType() {
        return type;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }
}
