package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import com.ISS.Booking_iss_tim21.repository.AccommodationReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationReviewService {

    @Autowired
    AccommodationReviewRepository repository;

    public List<AccommodationReview> getAll() {return repository.findAll();}
    public AccommodationReview findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void remove(Long id) { repository.deleteById(id);}
    public void save(AccommodationReview review) {repository.save(review);
    }
    public List<AccommodationReview> findAllByAccommodationId(Long id){
        return repository.findAllByAccommodationId(id);
    }
    public Double getAverageReview(Long accId){
        List<AccommodationReview> reviews=findAllByAccommodationId(accId);

        if(reviews.isEmpty()){
            return 0.0;
        }

        double sum=0.0;
        for(AccommodationReview review:reviews){
            sum+=review.getRating();
        }

        return (sum/reviews.size());
    }
}
