package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("select a from Accommodation a where a.owner.Id = ?1")
    public List<Accommodation> getOwnersAccommodations(Long ownerId);

    public List<Accommodation> getAccommodationsByLocation(String location);

    @Query("select a from Accommodation a where a.minGuests <= ?1 and a.maxGuests >= ?1")
    public List<Accommodation> getAccommodationsByNOGuests(int noGuests);

    @Query("select distinct a from Accommodation a join a.amenities as am where am in (:amenities) group by a.id having count(am) = :size")
    public List<Accommodation> getAccommodationsByAmenitiesIn(Collection<Amenity> amenities,int size);
    public List<Accommodation> getAccommodationsByTypeIn(List<AccommodationType> type);
}
