package com.kaggle.movies;

import com.kaggle.movies.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getByMoviesByGenres_returnsAllMoviesInGenre() throws Exception {

        ResponseEntity<Movie[]> response =  restTemplate.getForEntity("/movies?genre=Comedy", Movie[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie[] results = response.getBody();
        assertThat(results.length).isGreaterThanOrEqualTo(72);
    }

    @Test
    public void getByMoviesInBudgetRange_returnsProperRecords() throws Exception {

        ResponseEntity<Movie[]> response =  restTemplate.getForEntity("/movies?minBudget=3000000&maxBudget=6000000", Movie[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie[] results = response.getBody();
        assertThat(results.length).isGreaterThanOrEqualTo(7);
    }

    @Test
    public void getAllMovies_returnsAtLeast100() throws Exception {

        ResponseEntity<Movie[]> response =  restTemplate.getForEntity("/movies", Movie[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Movie[] results = response.getBody();
        assertThat(results.length).isGreaterThanOrEqualTo(100);

    }

    @Test
    public void getMovieByImdbId_returnsCorrectMovie() throws Exception {

        ResponseEntity<Movie> response =  restTemplate.getForEntity("/movies?imdbId=tt0114709", Movie.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getImdbId()).isEqualTo("tt0114709");
        assertThat(response.getBody().getTitle()).isEqualTo("Toy Story");

    }
}
