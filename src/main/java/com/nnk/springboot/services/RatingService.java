package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public Iterable<Rating> getRatingList() {
        return ratingRepository.findAll();
    }

    public Rating createNewRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating getRatingById(Integer id) {
        return ratingRepository.getById(id);
    }

    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }
}
