package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.CarTagRowMapper;
import com.burgas.springbootauto.model.CarTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarTagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarTagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CarTag> findAll() {
        return jdbcTemplate.query("select * from cars_tags", new CarTagRowMapper());
    }

    public CarTag findCarTag(int carId, int tagId) {
        return jdbcTemplate.queryForObject("select * from cars_tags where car_id=? and tag_id=?",
                new CarTagRowMapper(), carId, tagId);
    }

    public void save(CarTag carTag) {
        jdbcTemplate.update("insert into cars_tags values (?,?)", carTag.getCarId(), carTag.getTagId());
    }

    public void delete(CarTag carTag) {
        jdbcTemplate.update("delete from cars_tags where car_id=? and tag_id=?", carTag.getCarId(), carTag.getTagId());
    }
}
