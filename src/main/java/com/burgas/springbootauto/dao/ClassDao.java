package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.ClassRowMapper;
import com.burgas.springbootauto.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Class> findAll() {
        return jdbcTemplate.query("select * from classes", new ClassRowMapper());
    }

    public Class find(int id) {
        return jdbcTemplate.queryForObject("select * from classes where id=?", new ClassRowMapper(), id);
    }

    public void save(Class cl) {
        jdbcTemplate.update("insert into classes(name, image, description) VALUES (?,?,?)",
                cl.getName(), cl.getImage(), cl.getDescription());
    }

    public void update(Class cl) {
        jdbcTemplate.update("update classes set name=?,image=?,description=? where id=?",
                cl.getName(), cl.getImage(), cl.getDescription(), cl.getId());
    }

    public void delete(Class cl) {
        jdbcTemplate.update("delete from classes where id=?", cl.getId());
    }
}
