package com.kazama.springbootmall.rowmapper;

import com.kazama.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet rs , int rowNum ) throws SQLException{
        Order order = new Order();

        order.setOrder_id(rs.getInt("order_id"));
        order.setUser_id(rs.getInt("user_id"));
        order.setLast_modified_date(rs.getTimestamp("last_modified_date"));
        order.setCreated_date(rs.getTimestamp("created_date"));
        order.setTotal_amount(rs.getInt("total_amount"));
        return order;
    }
}
