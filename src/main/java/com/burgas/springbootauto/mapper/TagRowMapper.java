package com.burgas.springbootauto.mapper;

import com.burgas.springbootauto.model.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(@NotNull ResultSet resultSet, int rowNum) throws SQLException {
        return new Tag(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
