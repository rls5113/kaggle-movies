package com.kaggle.movies.repository;

import com.kaggle.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByImdbId(String imdbId);
}
