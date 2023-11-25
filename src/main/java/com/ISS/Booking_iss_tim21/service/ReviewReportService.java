package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.repository.ReviewReportRepository;
import com.ISS.Booking_iss_tim21.repository.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewReportService {

    @Autowired

    ReviewReportRepository repository;

    public List<ReviewReport> getAll(){
        return repository.findAll();
    }

    public ReviewReport findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }


}
