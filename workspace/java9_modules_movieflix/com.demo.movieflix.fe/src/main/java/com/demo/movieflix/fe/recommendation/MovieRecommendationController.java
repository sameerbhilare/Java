package com.demo.movieflix.fe.recommendation;

import java.util.List;
import java.util.logging.Logger;

public class MovieRecommendationController {
    
    private static final Logger log = Logger.getLogger(MovieRecommendationController.class.getName());

    public List<String> recommendMovies () {
        
        log.info("Recommending movies...");

        Module module = MovieRecommendationController.class.getModule();
        System.out.println("Module: "+module);
        System.out.println("Module name: "+module.getName());
        
        return List.of("Bad Boys","The Departed","Django Unchained");

    }
          
}
