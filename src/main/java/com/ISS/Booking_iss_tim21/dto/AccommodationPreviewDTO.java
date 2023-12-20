package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationPreviewDTO {
    private Long id;
    private String name;
    private String location;
    private String image;
    private Double price;

    public AccommodationPreviewDTO(Accommodation a) {

        setId(a.getId());
        setLocation(a.getLocation());
        setName(a.getName());
        setImage(a.getPhotos().isEmpty() ? null : a.getPhotos().get(0));
        setPrice(a.getPrice());

    }


}


