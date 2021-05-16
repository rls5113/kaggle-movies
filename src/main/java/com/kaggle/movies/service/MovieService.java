package com.kaggle.movies.service;

import com.kaggle.movies.exceptions.MovieNotFoundException;
import com.kaggle.movies.model.Movie;
import com.kaggle.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findByBudgetRange(String minBudget, String maxBudget) {
//        Long minLong = Long.getLong(minBudget);
//        Long maxLong = Long.getLong(maxBudget);

        List<Movie> result = movieRepository.findByBudget(minBudget, maxBudget);
//        List<Movie> result = movieRepository.findByBudget(minLong, maxLong);
        if(result == null || result.size() == 0 ){
            throw new MovieNotFoundException();
        }
        return result;
    }

    public List<Movie> findAllMovies() {
        List<Movie> result = movieRepository.findAll();
        if(result == null || result.size() == 0){
            throw new MovieNotFoundException();
        }
        return result;
    }

    public Movie findByImdbId(String imdbId) {
        Movie result = movieRepository.findByImdbId(imdbId);
        if(result == null) {
            throw new MovieNotFoundException();
        }
        return result;
    }
}
