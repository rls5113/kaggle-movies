# kaggle-movies

## ASSIGNMENT

Kaggle is a very cool data website. It has a lot of free datasets to be consumed.

For this exercise, we will consume a Movies Database csv file that will work as our database. https://www.kaggle.com/rounakbanik/the-movies-dataset?select=movies_metadata.csv

The goal is to build a Spring Boot application that will offer one /movies (GET) endpoint. This endpoint will accept some parameters:

* imdbId: the movie IMDB ID (string);
* genre: the movie genre (string);
* minBudget and maxBudget: the budget interval, in US Dollars (integer).
* When the parameters are provided, the endpoint should have logic to open the CSV and traverse its records, matching records with the parameters given. For instance:
* /movies?imdbDb=tt0114709 (fetches one record by the IMDB ID, in this case, 'Toy Story Collection');
* /movies?genre=Comedy (fetches all the records with the genre equal to Comedy);
* /movies?minBudget=1000000&maxBudget=50000000 (fetches all the movies with budgets between 1 and 50 million dollars).
* The results can be capped for the first 100 records, if the results get too large.
* Calling /movies without parameters is also an option.
* Unit Tests are expected, using jUnit and Mockito.

## IMPLEMENTATION DETAILS

* Data is read from CSV file into H2 in memory database on application startup.
* Original file from kaggle movies has been trimmed down to provide the header plus 199 records.
* H2 console is available at http://localhost:8080/h2-console [see application.properties for authentication].
* Postman requests are available for import at /src/test/resources/postman.
* _NOTE: I am calling out that I could not get the request to return GET by genres properly configured in time._
