package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.BrandRowMapper;
import com.burgas.springbootauto.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BrandDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Brand> findAll() {
        return jdbcTemplate.query("select * from brands", new BrandRowMapper());
    }

    public Brand find(int id) {
        return jdbcTemplate.queryForObject("select * from brands where id=?", new BrandRowMapper(), id);
    }

    public void save(Brand brand) {
        jdbcTemplate.update("insert into brands(title, image, website, description) VALUES (?,?,?,?)",
                brand.getTitle(),brand.getImage(),brand.getWebsite(),brand.getDescription()
        );
    }

    public void update(Brand brand) {
        jdbcTemplate.update("update brands set title=?, image=?, website=?, description=? where id=?",
                brand.getTitle(),brand.getImage(),brand.getWebsite(),brand.getDescription(),brand.getId()
        );
    }

    public void delete(Brand brand) {
        jdbcTemplate.update("delete from brands where id=?", brand.getId());
    }

    public List<Brand> search(String search) {
        //noinspection SqlSourceToSinkFlow
        return jdbcTemplate.query("select * from brands where concat(title,' ') ilike '%"+search+"%'",
                new BrandRowMapper()
        );
    }
}
