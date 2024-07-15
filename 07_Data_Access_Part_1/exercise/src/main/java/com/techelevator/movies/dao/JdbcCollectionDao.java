package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Collection> getCollections() {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT collection_id, collection_name " + "FROM collection;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            int id = results.getInt("collection_id");
            String name = results.getString("collection_name");

            Collection collection = new Collection(id, name);
            collections.add(collection);
        }
        return collections;
    }

    @Override
    public Collection getCollectionById(int id) {
        Collection collectionId = null;
        String sql = "SELECT collection_id, collection_name " + "FROM collection " + "WHERE collection_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            collectionId = mapRowToCollection(results);
        }
        return collectionId;
    }

    @Override
    public List<Collection> getCollectionsByName(String name, boolean useWildCard) {
        List<Collection> collections = new ArrayList<>();
        SqlRowSet results;
        if(useWildCard){
            name = "%" + name + "%";
        }
        if(name.isEmpty()){
            String sql = "SELECT collection_id, collection_name " + "FROM collection " + "WHERE collection_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        } else {
            String sql = "SELECT collection_id, collection_name " + "FROM collection " + "WHERE collection_name ILIKE ?;";
            results = jdbcTemplate.queryForRowSet(sql, name);
        }
        while (results.next()){
            collections.add(mapRowToCollection(results));
        }

        return collections;
    }

    private Collection mapRowToCollection(SqlRowSet rowSet){
        Collection collections = new Collection();
        collections.setId(rowSet.getInt("collection_id"));
        collections.setName(rowSet.getString("collection_name"));
        return collections;
    }
}
