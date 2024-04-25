package com.burgas.springbootauto.mapper;

import com.burgas.springbootauto.model.CarTag;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarTagRowMapper implements RowMapper<CarTag> {

    @Override
    public CarTag mapRow(@NotNull ResultSet resultSet, int rowNum) throws SQLException {
        return new CarTag(resultSet.getInt("car_id"), resultSet.getInt("tag_id"));
    }
}
