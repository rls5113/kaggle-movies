package com.kaggle.movies.repository;

import com.kaggle.movies.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void findByGenre_returnsMoviesInGenre() throws Exception {
        int numOfMovies = 10;

        List<Movie> moviesInGenre = new ArrayList<>();
        for(int i = 1; i <= numOfMovies; i++){
            if(i % 2 == 0) {
                Movie movie = new Movie(Long.parseLong(String.valueOf(i)),"My Movie"+i,"Comedy","tt00"+i,Long.parseLong("1000")*i);
                moviesInGenre.add(movie);
            }
        }
        for(Movie movie : moviesInGenre) {
            testEntityManager.persistAndFlush(movie);
        }

//        List<Movie> savedList = testEntityManager.persistFlushFind(moviesInGenre);
        List<Movie> result = movieRepository.findByGenres("Comedy");

        assertThat(result.size()).isEqualTo(moviesInGenre.size());
        assertThat(result.equals(moviesInGenre));
    }

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

        for(Movie movie : moviesInBudgetRange) {
            testEntityManager.persistAndFlush(movie);
        }
//        List<Movie> savedList = testEntityManager.persistFlushFind(moviesInBudgetRange);
        List<Movie> result = movieRepository.findByBudget(minBudget, maxBudget);

        assertThat(result.size()).isEqualTo(moviesInBudgetRange.size());
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

        for(Movie movie : manyMovies) {
            testEntityManager.persistAndFlush(movie);
        }
//        List<Movie> savedList = testEntityManager.persistFlushFind(manyMovies);
        List<Movie> result = movieRepository.findAll();

        assertThat(result.size()).isEqualTo(manyMovies.size());
        assertThat(result.equals(manyMovies));
    }

    @Test
    public void findByImdbId_returnsAMovie() throws Exception {

        Movie savedMovie = testEntityManager.persistFlushFind(new Movie(Long.parseLong("1"), "My Movie", "genres", "tt001", Long.parseLong("1000") ));
        Movie result = movieRepository.findByImdbId("tt001");

        assertThat(result.getId()).isEqualTo(savedMovie.getId());
        assertThat(result.getTitle()).isEqualTo(savedMovie.getTitle());
        assertThat(result.getGenres()).isEqualTo(savedMovie.getGenres());
        assertThat(result.getImdbId()).isEqualTo(savedMovie.getImdbId());
        assertThat(result.getBudget()).isEqualTo(savedMovie.getBudget());
    }
}