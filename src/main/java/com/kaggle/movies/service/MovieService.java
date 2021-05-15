package com.kaggle.movies.service;

import com.kaggle.movies.exceptions.MovieNotFoundException;
import com.kaggle.movies.model.Movie;
import com.kaggle.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findByImdbId(String imdbId) {
        Movie result = movieRepository.findByImdbId(imdbId);
        if(result == null) {
            throw new MovieNotFoundException();
        }
        return result;
    }
}
