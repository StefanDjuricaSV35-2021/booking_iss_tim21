package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Accommodation;

import java.util.Collection;

public interface AccommodationService {
    Collection<Accommodation> findAll();

    Accommodation findOne(Long id);

    Accommodation create(Accommodation accommodation) throws Exception;

    Accommodation update(Accommodation accommodation) throws Exception;

    void delete(Long id);
}
