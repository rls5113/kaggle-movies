package com.kaggle.movies.utility;

import com.kaggle.movies.model.Movie;
import com.kaggle.movies.repository.MovieRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvUtility {

    public static String TYPE = "text/csv";
    public static final String DATA_URL = "/data/movies_metadata.csv";
    static String[] headers = {"adult","belongs_to_collection","budget","genres","homepage",
            "id","imdb_id","original_language","original_title","overview",
            "popularity","poster_path","production_companies","production_countries","release_date",
            "revenue","runtime","spoken_languages","status","tagline",
            "title","video","vote_average","vote_count"};

    @Autowired
    MovieRepository movieRepository;

    @PostConstruct
    public void init() {
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DATA_URL)));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        ){
            List<Movie> movies = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            System.out.println("BEGIN LOADING DATA FROM CSV TO H2");
            for (CSVRecord csvRecord : csvRecords) {
                Movie movie = new Movie(
                        Long.parseLong(csvRecord.get(5)),
                        csvRecord.get(20),
                        csvRecord.get(3),
                        csvRecord.get(6),
                        Long.parseLong(csvRecord.get(2))
                );
                System.out.println(movie.toString());
                movies.add(movie);
            }
            movieRepository.saveAll(movies);
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            throw new RuntimeException("CSV file not found " + fne.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("Failed to parse CSV file: "+ ioe.getMessage());
        }
    }

}
