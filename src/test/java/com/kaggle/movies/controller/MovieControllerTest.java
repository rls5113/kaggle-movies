package com.kaggle.movies.controller;

import com.kaggle.movies.exceptions.MovieNotFoundException;
import com.kaggle.movies.model.Movie;
import com.kaggle.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void getMoviesByMinMaxBudget_shouldReturnWithinRange() throws Exception {
        int numOfMovies = 10;
        String minBudget = "3000", maxBudget="6000";

        List<Movie> moviesInBudgetRange = new ArrayList<>();
        for(int i = 1; i <= numOfMovies; i++){
            Movie movie = new Movie(Long.parseLong(String.valueOf(i)),"My Movie"+i,"genres","tt00"+i,Long.parseLong("1000")*i);
            if((movie.getBudget() >= Long.parseLong(minBudget) &&  movie.getBudget()  <= Long.parseLong(maxBudget))) {
                moviesInBudgetRange.add(movie);
            }
        }
        given(movieService.findByBudgetRange(anyString(),anyString())).willReturn(moviesInBudgetRange);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies?minBudget="+minBudget+"&maxBudget="+maxBudget))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllMovies_shouldReturnAll() throws Exception {
        int numOfMovies = 10;
        List<Movie> manyMovies = new ArrayList<>();
        for(int i = 1; i <= numOfMovies; i++){
            Movie movie = new Movie(Long.parseLong(String.valueOf(i)),"My Movie"+i,"genres","tt00"+i,Long.parseLong("1000")*i);
            manyMovies.add(movie);
        }
        given(movieService.findAllMovies()).willReturn(manyMovies);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMovieByImdbId_shouldReturnMovie() throws Exception {

        given(movieService.findByImdbId(anyString())).willReturn(new Movie(Long.parseLong("1"),"My Movie","genres","tt001",Long.parseLong("1000")));

        mockMvc.perform(MockMvcRequestBuilders.get("/movies?imdbId=tt001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(Long.parseLong("1")))
                .andExpect(jsonPath("title").value("My Movie"))
                .andExpect(jsonPath("imdbId").value("tt001"))
                .andExpect(jsonPath("genres").value("genres"))
                .andExpect(jsonPath("budget").value(Long.parseLong("1000")));
    }

    @Test
    public void getMovieByImdbIdNotInDB_throwsMovieNotFoundException() throws Exception {

        given(movieService.findByImdbId(anyString())).willThrow(new MovieNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/movies?imdbId=tt001"))
                .andExpect(status().isNotFound());
    }
}
