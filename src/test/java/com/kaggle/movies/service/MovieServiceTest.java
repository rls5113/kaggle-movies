package com.kaggle.movies.service;

import com.kaggle.movies.exceptions.MovieNotFoundException;
import com.kaggle.movies.model.Movie;
import com.kaggle.movies.repository.MovieRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void findByBudgetRange_returnsMoviesWithinRange() throws Exception {
        int numOfMovies = 10;
        String minBudget = "3000", maxBudget="6000";

        List<Movie> moviesInBudgetRange = new ArrayList<>();
        for(int i = 1; i <= numOfMovies; i++){
            Movie movie = new Movie(Long.parseLong(String.valueOf(i)),"My Movie"+i,"genres","tt00"+i,Long.parseLong("1000")*i);
            if((movie.getBudget() >= Long.parseLong(minBudget) &&  movie.getBudget()  <= Long.parseLong(maxBudget))) {
                moviesInBudgetRange.add(movie);
            }
        }
        given(movieRepository.findByBudget(anyString(),anyString())).willReturn(moviesInBudgetRange);
        List<Movie> result = movieRepository.findByBudget(minBudget, maxBudget);

//        given(movieRepository.findByBudget(anyLong(),anyLong())).willReturn(moviesInBudgetRange);
//        List<Movie> result = movieRepository.findByBudget(Long.parseLong(minBudget), Long.parseLong(maxBudget));

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.equals(moviesInBudgetRange));
    }

    @Test
    public void findAll_returnsAll() throws Exception {
        int numOfMovies = 10;
        List<Movie> manyMovies = new ArrayList<>();
        for(int i = 1; i <= numOfMovies; i++){
            Movie movie = new Movie(Long.parseLong(String.valueOf(i)),"My Movie"+i,"genres","tt00"+i,Long.parseLong("1000")*i);
            manyMovies.add(movie);
        }

        given(movieRepository.findAll()).willReturn(manyMovies);

        List<Movie> result = movieRepository.findAll();

        assertThat(result.size()).isEqualTo(10);
        assertThat(result.equals(manyMovies));

    }

    @Test
    public void getMovieByImdbId_shouldReturnMovie() throws Exception {

        given(movieRepository.findByImdbId("tt001")).willReturn(new Movie(Long.parseLong("1"),"My Movie","genres","tt001",Long.parseLong("1000")));

        Movie result = movieService.findByImdbId("tt001");

        assertThat(String.valueOf(result.getId())).isEqualTo("1");
        assertThat(String.valueOf(result.getTitle())).isEqualTo("My Movie");
        assertThat(String.valueOf(result.getGenres())).isEqualTo("genres");
        assertThat(String.valueOf(result.getImdbId())).isEqualTo("tt001");
        assertThat(String.valueOf(result.getBudget())).isEqualTo("1000");

    }

    @Test
    public void getMovieByImdbIdNotInDB_shouldReturnMovieNotFound() throws Exception {
        Assertions.assertThrows(MovieNotFoundException.class, () -> {
            movieService.findByImdbId("xxxx");
        });
    }

}