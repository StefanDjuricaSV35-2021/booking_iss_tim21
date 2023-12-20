package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationPricingRepository extends JpaRepository<AccommodationPricing, Long> {
    @Query("select a from AccommodationPricing a where a.accommodation.id = ?1")
    public List<AccommodationPricing> getAccommodationPricing(Long accommodationId);

    @Query(value="with recursive rec as " +
            "(select distinct accommodation_id as idd,(:dateFrom + :dayVal) as fr,price from accommodation_pricing ac " +
            "where ac.start_date<=:dateFrom and ac.end_date>=:dateFrom " +
            "union all " +
            "select r.idd,r.fr+:dayVal,foo.price from rec r " +
            "inner join " +
            "(select accommodation_id as idd,price,start_date, end_date from accommodation_pricing) " +
            "as foo " +
            "on foo.idd=r.idd and foo.start_date<=r.fr+:dayVal and foo.end_date>=r.fr+:dayVal and :dateTo!=r.fr) " +
            "select idd from rec group by(idd) having(count(*)=(:dateTo-:dateFrom)/:dayVal);",nativeQuery = true)
    public List<Long> getAccommodationIdsWithPrices(@Param("dateFrom") Long dateFrom, @Param("dateTo") Long dateTo,@Param("dayVal") Long dayVal);

    @Query(value="with recursive rec as " +
            "(select distinct accommodation_id as idd,(:dateFrom + :dayVal) as fr,price from accommodation_pricing ac " +
            "where ac.start_date<=:dateFrom and ac.end_date>=:dateFrom and ac.accommodation_id=:id " +
            "union all " +
            "select r.idd,r.fr+:dayVal,foo.price from rec r " +
            "inner join " +
            "(select accommodation_id as idd, price,start_date,end_date from accommodation_pricing ac " +
            "where ac.accommodation_id=:id) " +
            "as foo " +
            "on foo.idd=r.idd and foo.start_date<=r.fr+:dayVal and foo.end_date>=r.fr+:dayVal and :dateTo!=fr) " +
            "select SUM(price) from rec group by(idd) having(count(*)=(:dateTo-:dateFrom)/:dayVal);",nativeQuery = true)
    public Double getAccommodationTotalPriceDays(@Param("dateFrom") Long dateFrom, @Param("dateTo") Long dateTo, @Param("id") Long id,@Param("dayVal") Long dayVal);


    @Query(value="with recursive rec as " +
            "(select distinct accommodation_id as idd,(:dateFrom + :dayVal) as fr,(price * :noGuests) as price from accommodation_pricing ac " +
            "where ac.start_date<=:dateFrom and ac.end_date>=:dateFrom and ac.accommodation_id=:id " +
            "union all " +
            "select r.idd,r.fr+:dayVal,foo.price from rec r " +
            "inner join " +
            "(select accommodation_id as idd,(price * :noGuests) as price,start_date,end_date from accommodation_pricing ac " +
            "where ac.accommodation_id=:id) " +
            "as foo " +
            "on foo.idd=r.idd and foo.start_date<=r.fr+:dayVal and foo.end_date>=r.fr+:dayVal and :dateTo!=fr) " +
            "select SUM(price) from rec group by(idd) having(count(*)=(:dateTo-:dateFrom)/:dayVal);",nativeQuery = true)
    public Double getAccommodationTotalPriceGuests(@Param("dateFrom") Long dateFrom, @Param("dateTo") Long dateTo, @Param("id") Long id,@Param("noGuests") Integer noGuests,@Param("dayVal") Long dayVal);



    @Query("select timeSlot from AccommodationPricing where accommodation.id=:id")
    public List<TimeSlot> getAccommodationTimeSlots(@Param("id") Long id);




}
