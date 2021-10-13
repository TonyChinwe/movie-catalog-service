package com.phisoft.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.phisoft.moviecatalogservice.dto.Movie;
import com.phisoft.moviecatalogservice.dto.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getMovieInfoFallBack",

            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "5000")
            },

            threadPoolKey = "ratingThread",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "20"),
                    @HystrixProperty(name = "maxQueueSize",value = "10")
            }
    )
    public Movie getMovieInfo(Rating rating) {
        return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    }

    public Movie getMovieInfoFallBack(Rating rating){
        Movie movie=new Movie("Not found 123","Not Found");
        return movie;

    }
}
