package com.kaggle.movies.controller;

import com.kaggle.movies.exceptions.MovieNotFoundException;
import com.kaggle.movies.model.Movie;
import com.kaggle.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value="/movies", params={"minBudget", "maxBudget"})
    private List<Movie> getInBudgetRange(@RequestParam(value="minBudget") String minBudget, @RequestParam(value="maxBudget") String maxBudget) {
        return movieService.findByBudgetRange(minBudget, maxBudget);
    }

    @GetMapping(value="/movies", params={"imdbId"})
    private Movie getMovieByImdbId(@RequestParam(value="imdbId") String imdbId) {
        return movieService.findByImdbId(imdbId);
    }
    @GetMapping("/movies")
    private List<Movie> getAllMovies() {
        return movieService.findAllMovies();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void movieNotFoundHandler(MovieNotFoundException exception){}
}
