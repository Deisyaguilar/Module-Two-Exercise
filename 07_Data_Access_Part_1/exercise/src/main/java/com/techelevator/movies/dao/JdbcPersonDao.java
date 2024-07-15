package com.techelevator.movies.dao;

import com.techelevator.movies.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> getPersons() {

            List<Person> persons = new ArrayList<>();
            String sql = "SELECT * " + "FROM person;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                persons.add(mapRowToPerson(results));
            }
            return persons;
    }

    @Override
    public Person getPersonById(int id) {
        Person personId = null;
        String sql = "SELECT * " + "FROM person " + "WHERE person_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            personId = mapRowToPerson(results);
        }
        return personId;
    }

    @Override
    public List<Person> getPersonsByName(String name, boolean useWildCard) {
        List<Person> persons = new ArrayList<>();
        SqlRowSet results;
        if(useWildCard){
            name = "%" + name + "%";
        }
        if(name.isEmpty()){
            String sql = "SELECT * " + "FROM person " + "WHERE person_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        } else {
            String sql = "SELECT * " + "FROM person " + "WHERE person_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        }
        while (results.next()){
            persons.add(mapRowToPerson(results));
        }
        return persons;
    }

    @Override
    public List<Person> getPersonsByCollectionName(String collectionName, boolean useWildCard) {
        List<Person> persons = new ArrayList<>();
        SqlRowSet results;
        if(useWildCard){
            collectionName = "%" + collectionName + "%";
        }
        String sql = "SELECT DISTINCT p.person_id, p.person_name, p.birthday, p.deathday, p.biography, p.home_page, p.profile_path " +
                "FROM person p " + "JOIN movie_actor ma ON ma.actor_id = p.person_id " + "JOIN movie m ON m.movie_id = ma.movie_id " +
                "JOIN collection c ON c.collection_id = m.collection_id " + "WHERE collection_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, collectionName);
        while (results.next()){
            persons.add(mapRowToPerson(results));
        }
        return persons;
    }
    private Person mapRowToPerson(SqlRowSet rowSet){
        Person person = new Person();
        person.setId(rowSet.getInt("person_id"));
        person.setName(rowSet.getString("person_name"));
        if (rowSet.getDate("birthday") != null){
            person.setBirthday(rowSet.getDate("birthday").toLocalDate());
        }
        if (rowSet.getDate("deathday") != null){
            person.setDeathDate(rowSet.getDate("deathday").toLocalDate());
        }
        person.setBiography(rowSet.getString("biography"));
        person.setProfilePath(rowSet.getString("profile_path"));
        person.setHomePage(rowSet.getString("home_page"));
        return person;
    }
}
