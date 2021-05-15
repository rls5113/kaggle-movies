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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

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