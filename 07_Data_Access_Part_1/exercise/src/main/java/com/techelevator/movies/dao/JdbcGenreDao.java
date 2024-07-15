package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;
import com.techelevator.movies.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenreDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGenreDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT genre_id, genre_name " + "FROM genre;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            genres.add(mapRowToGenres(results));
        }
        return genres;
    }

    @Override
    public Genre getGenreById(int id) {
        Genre genreId = null;
        String sql = "SELECT genre_id, genre_name " + "FROM genre " + "WHERE genre_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            genreId = mapRowToGenres(results);
        }
        return genreId;
    }

    @Override
    public List<Genre> getGenresByName(String name, boolean useWildCard) {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet results;
        if(useWildCard){
            name = "%" + name + "%";
        }
        if(name.isEmpty()){
            String sql = "SELECT genre_id, genre_name " + "FROM genre " + "WHERE genre_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        } else {
            String sql = "SELECT genre_id, genre_name " + "FROM genre " + "WHERE genre_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        }
        while(results.next()){
            genres.add(mapRowToGenres(results));
        }

        return genres;
    }

    private Genre mapRowToGenres(SqlRowSet rowSet) {
        Genre genres = new Genre();
        genres.setId(rowSet.getInt("genre_id"));
        genres.setName(rowSet.getString("genre_name"));
        return genres;
    }
}