package com.kaggle.movies.utility;

import com.kaggle.movies.model.Movie;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvUtility {
    public static String TYPE = "text/csv";
    static String[] headers = {"adult","belongs_to_collection","budget","genres","homepage",
            "id","imdb_id","original_language","original_title","overview",
            "popularity","poster_path","production_companies","production_countries","release_date",
            "revenue","runtime","spoken_languages","status","tagline",
            "title","video","vote_average","vote_count"};
    public static boolean hasCSVFormat(MultipartFile file) {
        boolean isCSV = TYPE.equals(file.getContentType());
        return isCSV;
    }

    public static List<Movie> csv2Movie(InputStream is) {
//        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            CSVPar)
//        CSVParser parser = new CSVParser();
        return null;
    }


}
