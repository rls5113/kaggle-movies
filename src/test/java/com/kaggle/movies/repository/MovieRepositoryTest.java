package com.kaggle.movies.repository;

import com.kaggle.movies.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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