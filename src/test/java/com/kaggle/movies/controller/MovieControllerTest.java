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
