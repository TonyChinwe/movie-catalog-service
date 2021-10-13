package com.phisoft.moviecatalogservice.dto;

public class Rating {

    private String movieId;
    private int rate;

    public Rating() {
    }

    public Rating(String movieId, int rate) {
        this.movieId = movieId;
        this.rate = rate;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rate;
    }

    public void setRating(int rate) {
        this.rate = rate;
    }
}
