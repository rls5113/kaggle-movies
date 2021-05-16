package com.kaggle.movies.repository;

import com.kaggle.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByImdbId(String imdbId);

    List<Movie> findAll();
}
