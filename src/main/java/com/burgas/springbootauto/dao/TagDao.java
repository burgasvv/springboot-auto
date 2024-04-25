package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.TagRowMapper;
import com.burgas.springbootauto.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findAll() {
        return jdbcTemplate.query("select * from tags", new TagRowMapper());
    }

    public List<Tag> findByCarId(int id) {
        return jdbcTemplate.query(
                "select * from tags join cars_tags ct on tags.id = ct.tag_id where car_id = ?",
                new TagRowMapper(), id
        );
    }

    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject("select * from tags where name=?", new TagRowMapper(), name);
    }

    public Tag find(int id) {
        return jdbcTemplate.queryForObject("select * from tags where id=?", new TagRowMapper(), id);
    }

    public void save(Tag tag) {
        jdbcTemplate.update("insert into tags(name) values (?)", tag.getName());
    }

    public void update(Tag tag) {
        jdbcTemplate.update("update tags set name=? where id=?", tag.getName(), tag.getId());
    }

    public void delete(Tag tag) {
        jdbcTemplate.update("delete from tags where id=?", tag.getId());
    }
}
