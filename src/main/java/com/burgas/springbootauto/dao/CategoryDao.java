package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.CategoryRowMapper;
import com.burgas.springbootauto.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        return jdbcTemplate.query("select * from categories", new CategoryRowMapper());
    }

    public Category find(int id) {
        return jdbcTemplate.queryForObject("select * from categories where id=?", new CategoryRowMapper(), id);
    }

    public void save(Category category) {
        jdbcTemplate.update("insert into categories(name, image, description) values (?,?,?)", category.getName());
    }

    public void update(Category category) {
        jdbcTemplate.update("update categories set name=?, image=?,description=? where id=?",
                category.getName(), category.getImage(), category.getDescription(), category.getId());
    }

    public void delete(Category category) {
        jdbcTemplate.update("delete from categories where id=?", category.getId());
    }
}
