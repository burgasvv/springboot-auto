package com.burgas.springbootauto.mapper;

import com.burgas.springbootauto.model.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BrandRowMapper implements RowMapper<Brand> {

    @Override
    public Brand mapRow(@NotNull ResultSet resultSet, int rowNum) throws SQLException {
        return new Brand(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("image"),
                resultSet.getString("website"),
                resultSet.getString("description")
        );
    }
}
