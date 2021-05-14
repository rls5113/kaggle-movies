package com.kaggle.movies.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Movie {
    @Id
    private long id;
    private String title;
    private String genres;
    private String imdbId;
    private long budget;

}
