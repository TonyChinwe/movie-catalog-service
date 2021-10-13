package com.phisoft.moviecatalogservice.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.phisoft.moviecatalogservice.dto.CatalogItem;
import com.phisoft.moviecatalogservice.dto.Movie;
import com.phisoft.moviecatalogservice.dto.Rating;
import com.phisoft.moviecatalogservice.dto.UserRating;
import com.phisoft.moviecatalogservice.service.MovieInfo;
import com.phisoft.moviecatalogservice.service.MovieRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private MovieRating movieRating;

    @GetMapping("/{id}")
    public List<CatalogItem> getCaltalog(@PathVariable("id") String id){
        UserRating userRating = movieRating.getUserRating(id);
     return userRating.getRatings().stream().map(rating ->{
           Movie movie= movieInfo.getMovieInfo(rating);
           return new CatalogItem(movie.getName(),movie.getName(),movie.getMovieId());
       }).collect(Collectors.toList());
    }

}


//          Movie movie=webClientBuilder.build().get()
//                      .uri("http://localhost:8082/movies/"+rating.getMovieId())
//                      .retrieve().bodyToMono(Movie.class)
//                       .block();
