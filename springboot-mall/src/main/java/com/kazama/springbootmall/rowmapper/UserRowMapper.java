package com.kazama.springbootmall.rowmapper;

import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setPassword(rs.getString("password"));
        user.setCreated_date(rs.getDate("created_date"));
        user.setUser_id(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setLast_modified_date(rs.getDate("last_modified_date"));

        return user;


    }
}
