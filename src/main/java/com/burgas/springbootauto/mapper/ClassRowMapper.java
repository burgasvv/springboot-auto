package com.burgas.springbootauto.mapper;

import com.burgas.springbootauto.model.Class;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassRowMapper implements RowMapper<Class> {

    @Override
    public Class mapRow(@NotNull ResultSet resultSet, int rowNum) throws SQLException {
        return new Class(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("image"),
                resultSet.getString("description")
        );
    }
}
