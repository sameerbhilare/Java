package com.demo.movieflix.be.review.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Logger;

import com.demo.movieflix.be.review.ReviewCalculator;


public class ReviewService {

    private static final Logger log = Logger.getLogger(ReviewService.class.getName());

    private final ReviewCalculator reviewCalculator = new ReviewCalculator();

    private Map<String,Integer> movieRating = new HashMap<>();

    public Map<String,Integer> getReviews(List<String> movies){
        log.info("Fetching reviews for movies...");

        Module module = ReviewService.class.getModule();
        System.out.println("Review Service, Module: "+module);
        System.out.println("Review Service, Module name: "+module.getName());

        for(String movie : movies){
            movieRating.put(movie,reviewCalculator.getRating(movie));
        }
        return movieRating;
        
    }
          
}
