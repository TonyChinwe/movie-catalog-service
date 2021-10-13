package com.phisoft.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.phisoft.moviecatalogservice.dto.Rating;
import com.phisoft.moviecatalogservice.dto.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MovieRating {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getUserRatingFallBack",
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
    public UserRating getUserRating(@PathVariable("id") String id) {
        return restTemplate.getForObject("http://movie-rating-service/rating/users/"+id, UserRating.class);
    }

   public UserRating getUserRatingFallBack(@PathVariable("id") String id){
        UserRating userRating=new UserRating();
        userRating.setRatings(Arrays.asList(new Rating("Not found Id",1)));
        return userRating;
    }



}
