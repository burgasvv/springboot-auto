package com.burgas.springbootauto.mapper;

import com.burgas.springbootauto.model.Car;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(@NotNull ResultSet resultSet, int rowNum) throws SQLException {
        return new Car(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getInt("brand_id"),
                resultSet.getInt("class_id"),
                resultSet.getInt("category_id"),
                resultSet.getString("image"),
                resultSet.getString("webpage"),
                resultSet.getString("description"),
                resultSet.getInt("price")
        );
    }
}
