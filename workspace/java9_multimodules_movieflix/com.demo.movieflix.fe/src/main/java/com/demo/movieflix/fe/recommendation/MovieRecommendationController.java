package com.demo.movieflix.fe.recommendation;


import java.util.List;
import java.util.Map;
import com.demo.movieflix.be.review.service.ReviewService;
//import com.demo.movieflix.be.review.ReviewCalculator;


public class MovieRecommendationController {

    public Map<String,Integer> recommendMovies () {

        Module module = MovieRecommendationController.class.getModule();
        System.out.println("MovieRecommendationController, Module: "+module);
        System.out.println("MovieRecommendationController, Module name: "+module.getName());

        /*ReviewCalculator reviewCalculator  = new ReviewCalculator();
        reviewCalculator.getRating("Bad Boys");*/

        ReviewService reviewService = new ReviewService();
        return reviewService.getReviews(getMovies());
    }

    private List<String> getMovies(){
        return List.of("Bad Boys","The Departed","Django Unchained");
    }
          
}
