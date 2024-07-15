package com.techelevator.movies.dao;

import com.techelevator.movies.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * " + "FROM movie;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            movies.add(mapRowToMovie(results));
        }
        return movies;
    }

    @Override
    public Movie getMovieById(int id) {
        Movie movieId = null;
        String sql = "SELECT * " + "FROM movie " + "WHERE movie_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            movieId = mapRowToMovie(results);
        }
        return movieId;
    }

    @Override
    public List<Movie> getMoviesByTitle(String title, boolean useWildCard) {
        List<Movie> movies = new ArrayList<>();
        SqlRowSet results;
        if(useWildCard){
            title = "%" + title + "%";
        }
        if(title.isEmpty()){
            String sql = "SELECT * " + "FROM movie " + "WHERE title ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, title);
        } else {
            String sql = "SELECT * " + "FROM movie " + "WHERE title ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, title);
        }
        while(results.next()){
            movies.add(mapRowToMovie(results));
        }
        return movies;
    }

    @Override
    public List<Movie> getMoviesByDirectorNameAndBetweenYears(String directorName, int startYear,
                                                              int endYear, boolean useWildCard) {
        List<Movie> movies = new ArrayList<>();
        if(useWildCard){
            directorName = "%" + directorName + "%";
        }
            String sql = "SELECT * " + "FROM movie " + "WHERE director_id IN ( " + "SELECT person_id " + "FROM person " + "WHERE person_name ILIKE ?) "
                    + "AND release_date > CAST(? AS date) AND release_date < CAST(? AS date) " + "ORDER BY release_date;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, directorName, startYear + "-01-01", endYear + "-12-31");
            while (results.next()){
                movies.add(mapRowToMovie(results));
            }

        return movies;
    }
    private Movie mapRowToMovie(SqlRowSet rowSet){
        Movie movie = new Movie();
        movie.setId(rowSet.getInt("movie_id"));
        movie.setTitle(rowSet.getString("title"));
        movie.setReleaseDate(rowSet.getDate("release_date").toLocalDate());
        movie.setDirectorId(rowSet.getInt("director_id"));

        movie.setCollectionId(rowSet.getInt("collection_id"));
        movie.setHomePage(rowSet.getString("home_page"));
        movie.setLengthMinutes(rowSet.getInt("length_minutes"));
        movie.setOverview(rowSet.getString("overview"));
        movie.setPosterPath(rowSet.getString("poster_path"));
        movie.setTagline(rowSet.getString("tagline"));

        return movie;
    }
}
