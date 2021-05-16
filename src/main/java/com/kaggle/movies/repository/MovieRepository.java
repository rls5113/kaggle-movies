package com.kaggle.movies.repository;

import com.kaggle.movies.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findByImdbId(String imdbId);

    List<Movie> findAll();

    @Query(nativeQuery = true, value="SELECT m.id, m.title, m.genres, m.imdb_id, m.budget  FROM Movie m WHERE m.budget >= CAST(:minBudget AS BIGINT) and m.budget <= CAST(:maxBudget AS BIGINT) ")
    List<Movie> findByBudget(@RequestParam("minBudget") String minBudget, @RequestParam("maxBudget") String maxBudget);

//    @Query(nativeQuery = true, value="SELECT m.id, m.title, m.genres, m.imdb_id, m.budget  FROM Movie m WHERE :genre in JSON_OBJECT(m.genres)) ")
    @Query(nativeQuery = true, value="SELECT m.id, m.title, m.genres, m.imdb_id, m.budget  FROM Movie m WHERE m.genres LIKE '%'||:genre||'%') ")
    List<Movie> findByGenres(@RequestParam("genre") String genre);
}
